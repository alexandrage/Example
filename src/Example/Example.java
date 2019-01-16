package Example;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import Example.similar.Similar;

import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Example {
	public static Location setDirection(Location loc, Location lookat) {
		loc = loc.clone();
		double dx = lookat.getX() - loc.getX();
		double dy = lookat.getY() - loc.getY();
		double dz = lookat.getZ() - loc.getZ();
		if (dx != 0) {
			if (dx < 0) {
				loc.setYaw((float) (1.5 * Math.PI));
			} else {
				loc.setYaw((float) (0.5 * Math.PI));
			}
			loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
		} else if (dz < 0) {
			loc.setYaw((float) Math.PI);
		}
		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
		loc.setPitch((float) -Math.atan(dy / dxz));
		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
		return loc;
	}

	public static boolean checkremove(Player p, ItemStack s, int c) {
		if (calc(p, s, Similar.material) >= c) {
			clear(p, s, c, Similar.material);
			return true;
		}
		return false;
	}

	public static int calc(Player p, ItemStack s, Similar is) {
		int count = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if (stack == null)
				continue;
			if (Similar.has(stack, s, is)) {
				count += stack.getAmount();
			}
		}
		return count;
	}

	public static void clear(Player p, ItemStack s, int c, Similar is) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if (stack == null)
				continue;
			if (Similar.has(stack, s, is)) {
				if (stack.getAmount() == 0)
					break;
				if (stack.getAmount() <= c) {
					c = c - stack.getAmount();
					stack.setAmount(-1);
				}
				if (stack.getAmount() > c) {
					stack.setAmount(stack.getAmount() - c);
					c = 0;
				}
			}
		}
	}

	public static List<String> s(String st, int spl) {
		String[] arrWords = st.split(" ");
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		int index = 0;
		int length = arrWords.length;
		while (index != length) {
			if (sb.length() + arrWords[index].length() <= spl) {
				sb.append(arrWords[index]).append(" ");
				index++;
			} else {
				list.add(sb.toString());
				sb.setLength(0);
			}
		}
		if (sb.length() > 0) {
			list.add(sb.toString());
		}

		sb = null;
		return list;
	}

	public static void setShieldMeta(ItemStack is, String basecolor, String patternColor, String patternType) {
		if (is.getType() == Material.SHIELD) {
			ItemMeta meta = is.getItemMeta();
			BlockStateMeta bmeta = (BlockStateMeta) meta;
			Banner banner = (Banner) bmeta.getBlockState();
			banner.setBaseColor(DyeColor.valueOf(basecolor));
			banner.addPattern(new Pattern(DyeColor.valueOf(patternColor), PatternType.valueOf(patternType)));
			banner.update();
			bmeta.setBlockState(banner);
			is.setItemMeta(bmeta);
		}
	}

	public static void setShieldMeta(ItemStack is, String basecolor) {
		if (is.getType() == Material.SHIELD) {
			ItemMeta meta = is.getItemMeta();
			BlockStateMeta bmeta = (BlockStateMeta) meta;
			Banner banner = (Banner) bmeta.getBlockState();
			banner.setBaseColor(DyeColor.valueOf(basecolor));
			banner.update();
			bmeta.setBlockState(banner);
			is.setItemMeta(bmeta);
		}
	}

	public static void sendActionBarMessage(Player p, String message) {
		PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
		chat.getChatTypes().write(0, ChatType.GAME_INFO);
		chat.getChatComponents().write(0, WrappedChatComponent.fromText(message));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void setFacingDirection(BlockFace face, Block block) {
		BlockState state = block.getState();
		MaterialData materialData = state.getData();
		if (materialData instanceof Directional) {
			Directional directional = (Directional) materialData;
			directional.setFacingDirection(face);
			state.update();
		}
	}

	public static List<String> list(List<String> list, int tsize) {
		return list.stream().limit(tsize).collect(Collectors.toList());
	}

	public static <T extends Object> List<List<T>> split(List<T> list, int targetSize) {
		List<List<T>> lists = new ArrayList<List<T>>();
		for (int i = 0; i < list.size(); i += targetSize) {
			lists.add(list.subList(i, Math.min(i + targetSize, list.size())));
		}
		return lists;
	}

	public static void addRecipe(Plugin plugin, ItemStack stack, ItemStack[] istack) {
		ShapedRecipe rc = new ShapedRecipe(new NamespacedKey(plugin, UUID.randomUUID().toString()), stack);
		rc.shape("012", "345", "678");
		for (int i = 0; i < 9; i++) {
			if (istack[i] != null && istack[i].getType() != Material.AIR)
				rc.setIngredient(Character.forDigit(i, 10), istack[i].getType());
		}
		Bukkit.getServer().addRecipe(rc);
	}

	public static Block getTarget(Player player, Integer range) {
		BlockIterator bi = new BlockIterator(player, range);
		Block lastBlock = bi.next();
		while (bi.hasNext()) {
			lastBlock = bi.next();
			if (lastBlock.getType() == Material.AIR) {
				continue;
			}
			break;
		}
		return lastBlock;
	}

	public static boolean isInteger(String s) {
		if (s.startsWith("-")) {
			s = s.substring(1);
		}
		if (s.length() > 10) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (Character.digit(s.charAt(i), 10) < 0) {
				return false;
			}
		}
		if (s.length() == 10) {
			return Long.parseLong(s) <= Integer.MAX_VALUE;
		}
		return true;
	}

	public static String pluralForm(long i, String form1, String form2, String form5) {
		i = i % 100;
		long n1 = i % 10;
		if (i > 10 && i < 20) {
			return form5;
		}
		if (n1 > 1 && n1 < 5) {
			return form2;
		}
		if (n1 == 1) {
			return form1;
		}
		return form5;
	}

	public static String ticksToDate(long ticks) {
		ticks = ticks - 18000L + 24000L;
		long days = ticks / 24000L;
		ticks -= days * 24000L;
		long hours = ticks / 1000L;
		ticks -= hours * 1000L;
		long minutes = (long) Math.floor(ticks / 16.666666666666668D);
		double dticks = ticks - minutes * 16.666666666666668D;
		long seconds = (long) Math.floor(dticks / 0.2777777777777778D);
		Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.ENGLISH);
		cal.setLenient(true);
		cal.set(0, 0, 1, 0, 0, 0);
		cal.add(6, (int) days);
		cal.add(11, (int) hours);
		cal.add(12, (int) minutes);
		cal.add(13, (int) seconds + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		return sdf.format(cal.getTime());
	}

	public static void sendActionBar(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
}