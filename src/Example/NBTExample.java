package Example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.TileEntity;

public class NBTExample {

	public static ItemStack setSkullSkin(ItemStack item, String name) {
		NBTTagCompound tag = new NBTTagCompound();
		try {
			String uuid = getUUID(name);
			try {
				tag = MojangsonParser.parse(nbt(getSkinProfile(uuid)));
			} catch (MojangsonParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		NBTTagCompound nbt = new NBTTagCompound();
		CraftItemStack.asNMSCopy(item).save(nbt);
		nbt.set("tag", tag);
		return CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(nbt));
	}

	public static void setSkullSkin(Block block, Location loc, String name) {
		try {
			String uuid = getUUID(name);
			CraftWorld cw = (CraftWorld) loc.getWorld();
			TileEntity tile = cw.getHandle().getTileEntity(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()));
			NBTTagCompound NBT = new NBTTagCompound();
			tile.save(NBT);
			NBT = MojangsonParser.parse(nbt(getSkinProfile(uuid), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
					NBT.get("Rot").toString()));
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

	private static String nbt(JsonElement jsone, int x, int y, int z, String rot) {
		JsonElement prop = jsone.getAsJsonObject().get("properties");
		JsonObject json = prop.getAsJsonArray().get(0).getAsJsonObject();
		String id = jsone.getAsJsonObject().get("id").getAsString();
		String name = jsone.getAsJsonObject().get("name").toString();
		String value = json.get("value").toString();
		BigInteger b = new BigInteger(id, 16);
		String tmp = "{Owner:{Id:\"" + new UUID(b.shiftRight(64).longValue(), b.longValue())
				+ "\",Properties:{textures:[{Value:" + value + "}]},Name:" + name + "}" + ",Rot:" + rot + ",x:" + x
				+ ",y:" + y + ",z:" + z + ",id:\"minecraft:skull\",SkullType:3b}";
		return tmp;
	}

	private static String getUUID(String name) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) setupConnection(
				new URL("https://api.mojang.com/profiles/minecraft"));
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		writer.write(new Gson().toJson(Arrays.asList(new String[] { name })).getBytes(StandardCharsets.UTF_8));
		writer.flush();
		writer.close();
		InputStream is = connection.getInputStream();
		JsonParser parse = new JsonParser();
		String result = IOUtils.toString(is, StandardCharsets.UTF_8);
		if (result.length() > 2) {
			JsonElement jsone = parse.parse(result).getAsJsonArray().get(0);
			return jsone.getAsJsonObject().get("id").getAsString();
		} else {
			return null;
		}
	}

	private static JsonElement getSkinProfile(String id) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) setupConnection(
				new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id.replace("-", "")
						+ "?unsigned=true"));
		if (connection.getResponseCode() == 429) {
			throw new IOException("RATE LIMITED");
		}
		InputStream is = connection.getInputStream();
		String result = IOUtils.toString(is, StandardCharsets.UTF_8);
		JsonParser parse = new JsonParser();
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
}