package Example;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;

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
		if (calc(p, s) >= c) {
			clear(p, s, c);
			return true;
		}
		return false;
	}

	public static int calc(Player p, ItemStack s) {
		int count = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if (stack == null)
				continue;
			if (stack.isSimilar(s)) {
				count += stack.getAmount();
			}
		}
		return count;
	}

	public static void clear(Player p, ItemStack s, int c) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if (stack == null)
				continue;
			if (stack.isSimilar(s)) {
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
		String str = st.substring(1, st.length() - 1);
		String[] arrWords = str.split(" ");
		ArrayList<String> arrPhrases = new ArrayList<String>();
		StringBuilder stringBuffer = new StringBuilder();
		int cnt = 0;
		int index = 0;
		int length = arrWords.length;
		while (index != length) {
			if (cnt + arrWords[index].length() <= spl) {
				cnt += arrWords[index].length() + 1;
				stringBuffer.append(arrWords[index]).append(" ");
				index++;
			} else {
				arrPhrases.add(stringBuffer.toString());
				stringBuffer = new StringBuilder();
				cnt = 0;
			}
		}
		if (stringBuffer.length() > 0) {
			arrPhrases.add(stringBuffer.toString());
		}

		List<String> list = new ArrayList<String>();
		Iterator<String> it = arrPhrases.iterator();
		while (it.hasNext()) {
			String elem = (String) it.next();
			list.add(elem);
		}
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

	public void sendActionBarMessage(Player p, String message) {
		PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
		chat.getChatTypes().write(0, ChatType.GAME_INFO);
		chat.getChatComponents().write(0, WrappedChatComponent.fromJson(s(message)));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public String s(String s) {
		return "{\"text\": \"" + s + "\"}";
	}
}