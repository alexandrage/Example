package Example.event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.comphenix.packetwrapper.WrapperPlayServerBlockAction;
import com.comphenix.protocol.wrappers.BlockPosition;
import Example.Main;
import Example.nms.NBTExample;
import Example.runs.ArmorStandScheduler;

public class EventListener implements Listener {
	private Main plugin;

	public EventListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void on(HangingBreakByEntityEvent e) {
		if (e.getRemover().getType() == EntityType.PLAYER) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void on(BlockBreakEvent e) {
		Block b = e.getBlock();
		Location loc = b.getLocation();
		String block = b.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ()
				+ ":" + b.getTypeId() + ":" + b.getData();
		//Scheduler.blocks.put(block, 100l);
	}

	private static CommandMap map;

	public static String get(String name) {
		try {
			return map.getCommand(name).getName();
		} catch (Exception e) {
		}
		return "-";
	}

	static {
		Field field;
		try {
			field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			map = (CommandMap) field.get(Bukkit.getServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void on(CraftItemEvent e) throws Exception {
		Location loc = NBTExample.getPosition(e);
		System.out.println(loc.getX() + " " + loc.getY() + " " + loc.getZ());
	}

	public void sendBlockAction(Location loc, Player player, int i) {
		WrapperPlayServerBlockAction packet = new WrapperPlayServerBlockAction();
		packet.setBlockType(Material.CHEST);
		packet.setByte1(1);
		packet.setByte2(i);
		packet.setLocation(new BlockPosition(loc.toVector()));
		packet.sendPacket(player);
		if (i == 1) {
			player.playSound(loc, Sound.BLOCK_CHEST_OPEN, 1, i);
		}
		if (i == 0) {
			player.playSound(loc, Sound.BLOCK_CHEST_CLOSE, 1, i);
		}
	}
	
	public void runs(Location start) {
		List<Location> circle = new ArrayList<Location>();
		for (double t = 0; t < 2 * Math.PI; t += 0.1) {
			Location news = start.add(0.2 * Math.cos(t), 0.2 * Math.sin(t), 0);
			circle.add(news.clone());
		}
		new Thread(new ArmorStandScheduler(circle, get())).start();
	}

	List<ItemStack> get() {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(create("VIP1", Material.APPLE));
		stacks.add(create("VIP2", Material.ARROW));
		stacks.add(create("VIP3", Material.BONE));
		stacks.add(create("VIP4", Material.COAL_ORE));
		stacks.add(create("VIP5", Material.DIAMOND));
		stacks.add(create("VIP6", Material.EGG));
		stacks.add(create("VIP7", Material.GOLD_AXE));
		stacks.add(create("VIP8", Material.WOOD_AXE));
		stacks.add(create("VIP9", Material.IRON_AXE));
		stacks.add(create("VIP10", Material.STICK));
		stacks.add(create("VIP11", Material.IRON_PICKAXE));
		stacks.add(create("VIP12", Material.DIAMOND_PICKAXE));
		stacks.add(create("VIP13", Material.DIAMOND_SWORD));
		return stacks;
	}

	ItemStack create(String name, Material m) {
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		stack.setItemMeta(meta);
		return stack;
	}
}