package Example.nms;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ContainerWorkbench;
import net.minecraft.server.v1_13_R2.Item;
import net.minecraft.server.v1_13_R2.ItemCooldown;
import net.minecraft.server.v1_13_R2.MojangsonParser;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.SoundCategory;
import net.minecraft.server.v1_13_R2.SoundEffect;
import net.minecraft.server.v1_13_R2.SoundEffectType;
import net.minecraft.server.v1_13_R2.TileEntity;

public class NBTExample {

	public static ItemStack setSkullSkin(ItemStack item, String name) {
		NBTTagCompound tag = new NBTTagCompound();
		try {
			String uuid = getUUID(name);
			try {
				tag = MojangsonParser.parse(nbt(getSkinProfile(uuid)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		NBTTagCompound nbt = new NBTTagCompound();
		CraftItemStack.asNMSCopy(item).save(nbt);
		nbt.set("tag", tag);
		return CraftItemStack.asBukkitCopy(net.minecraft.server.v1_13_R2.ItemStack.a(nbt));
	}

	public static void setSkullSkin(Block block, Location loc, String name) {
		try {
			String uuid = getUUID(name);
			CraftWorld cw = (CraftWorld) loc.getWorld();
			TileEntity tile = cw.getHandle().getTileEntity(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()));
			NBTTagCompound NBT = new NBTTagCompound();
			tile.save(NBT);
			NBT = MojangsonParser.parse(nbt(getSkinProfile(uuid), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
			tile.load(NBT);
			block.getState().update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String nbt(JsonElement jsone) {
		JsonElement prop = jsone.getAsJsonObject().get("properties");
		JsonObject json = prop.getAsJsonArray().get(0).getAsJsonObject();
		String id = jsone.getAsJsonObject().get("id").getAsString();
		String name = jsone.getAsJsonObject().get("name").toString();
		String value = json.get("value").toString();
		BigInteger b = new BigInteger(id, 16);
		String tmp = "{SkullOwner:{Id:\"" + new UUID(b.shiftRight(64).longValue(), b.longValue())
				+ "\",Properties:{textures:[{Value:" + value + "}]},Name:" + name + "}}";
		return tmp;
	}

	private static String nbt(JsonElement jsone, int x, int y, int z) {
		JsonElement prop = jsone.getAsJsonObject().get("properties");
		JsonObject json = prop.getAsJsonArray().get(0).getAsJsonObject();
		String id = jsone.getAsJsonObject().get("id").getAsString();
		String name = jsone.getAsJsonObject().get("name").toString();
		String value = json.get("value").toString();
		BigInteger b = new BigInteger(id, 16);
		String tmp = "{Owner:{Id:\"" + new UUID(b.shiftRight(64).longValue(), b.longValue())
				+ "\",Properties:{textures:[{Value:" + value + "}]},Name:" + name + "}" + ",x:" + x + ",y:" + y + ",z:"
				+ z + ",id:\"minecraft:skull\"}";
		return tmp;
	}

	public static String getUUID(String name) throws IOException {
		File file = new File("./uuids/" + name + ".json");
		file.getParentFile().mkdirs();
		if (file.exists()) {
			return FileUtils.readFileToString(file, Charset.defaultCharset());
		}
		HttpURLConnection connection = (HttpURLConnection) setupConnection(
				new URL("https://api.mojang.com/profiles/minecraft"));
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		writer.write(new Gson().toJson(Arrays.asList(new String[] { name })).getBytes(StandardCharsets.UTF_8));
		writer.flush();
		writer.close();
		InputStream is = connection.getInputStream();
		String result = IOUtils.toString(is, StandardCharsets.UTF_8);
		if (result.length() > 2) {
			JsonParser parse = new JsonParser();
			JsonElement jsone = parse.parse(result).getAsJsonArray().get(0);
			FileUtils.writeStringToFile(file, jsone.getAsJsonObject().get("id").getAsString(),
					Charset.defaultCharset());
			return jsone.getAsJsonObject().get("id").getAsString();
		} else {
			return null;
		}
	}

	public static JsonElement getSkinProfile(String id) throws IOException {
		File file = new File("./profile/" + id + ".json");
		file.getParentFile().mkdirs();
		JsonParser parse = new JsonParser();
		if (file.exists()) {
			return parse.parse(FileUtils.readFileToString(file, Charset.defaultCharset()));
		}
		HttpURLConnection connection = (HttpURLConnection) setupConnection(
				new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id.replace("-", "")
						+ "?unsigned=true"));
		if (connection.getResponseCode() == 429) {
			throw new IOException("RATE LIMITED");
		}
		InputStream is = connection.getInputStream();
		String result = IOUtils.toString(is, StandardCharsets.UTF_8);
		FileUtils.writeStringToFile(file, result, Charset.defaultCharset());
		return parse.parse(result);
	}

	private static URLConnection setupConnection(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

	@EventHandler
	public static Location getPosition(CraftItemEvent e) throws Exception {
		BlockPosition bp = null;
		if (e.getInventory().getType() == InventoryType.WORKBENCH) {
			CraftInventoryView inv = (CraftInventoryView) e.getView();
			bp = (BlockPosition) field.get(inv.getHandle());
		}
		if (bp == null) {
			return new Location(e.getWhoClicked().getWorld(), 0, 0, 0, 0, 0);
		}
		return new Location(e.getWhoClicked().getWorld(), bp.getX(), bp.getY(), bp.getZ(), 0, 0);
	}

	private static Field field = null;
	static {
		try {
			field = ContainerWorkbench.class.getDeclaredField("h");
			field.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void on(BlockBreakEvent e) {
		try {
			Block block = e.getBlock();
			Location loc = block.getLocation();
			Method method = CraftBlock.class.getDeclaredMethod("getNMSBlock");
			method.setAccessible(true);
			net.minecraft.server.v1_13_R2.Block b = (net.minecraft.server.v1_13_R2.Block) method.invoke(block);
			SoundEffectType soundeffecttype = b.getStepSound();
			CraftWorld w = (CraftWorld) loc.getWorld();
			net.minecraft.server.v1_13_R2.World world = w.getHandle();
			Field field = SoundEffectType.class.getDeclaredField("o");
			field.setAccessible(true);
			SoundEffect se = (SoundEffect) field.get(soundeffecttype);
			world.a(null, loc.getX(), loc.getY(), loc.getZ(), se, SoundCategory.NEUTRAL, soundeffecttype.a(), 0.8f);
			block.setType(Material.AIR);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean isCooldown(Player player, ItemStack stack, int time) {
		net.minecraft.server.v1_13_R2.ItemStack cstack = CraftItemStack.asNMSCopy(stack);
		Item item = cstack.getItem();
		CraftPlayer cp = (CraftPlayer) player;
		ItemCooldown itemCooldown = cp.getHandle().getCooldownTracker();
		boolean bool = itemCooldown.a(item);
		if (!bool) {
			itemCooldown.a(item, time);
		}
		return bool;
	}
}