package Example.event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.packetwrapper.WrapperPlayServerBlockAction;
import com.comphenix.protocol.wrappers.BlockPosition;

import Example.Main;
import Example.fakechest.Menu;
import Example.nms.NBTExample;
import Example.node.NodeUtils;
import Example.runs.ArmorStandScheduler;
import Example.runs.SchedulerP;
import net.ess3.api.IEssentials;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class EventListener implements Listener {
	private Main plugin;

	public EventListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public static void sendMessage(Player player, String pref, String hov, String mess) {
		BaseComponent[] prefix = TextComponent.fromLegacyText(pref);
		BaseComponent name = new TextComponent(player.getName());
		name.copyFormatting(prefix[prefix.length-1]);
		HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hov));
		name.setHoverEvent(hover);
		BaseComponent[] message = TextComponent.fromLegacyText(mess);
		List<BaseComponent> list = new ArrayList<BaseComponent>();
		for(BaseComponent base : prefix) {
			list.add(base);
		}
		list.add(name);
		for(BaseComponent base : message) {
			list.add(base);
		}
		player.spigot().sendMessage(list.toArray(new BaseComponent[]{}));
	}

	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player player = e.getPlayer(); 
		sendMessage(player, "§4prefix-§3", "hover", "§6 message");
	}

	@EventHandler
	public void on(SignChangeEvent e) {
		
	}

	@EventHandler
	public void on(PlayerJoinEvent e) {
		// List<String> list = null;
		// e.getPlayer().sendMessage(list.toArray(new String[] {}));
		// this.plugin.cfgs.add(this.plugin, e.getPlayer().getName(), false);
	}

	@EventHandler
	public void on(EntityExplodeEvent e) {

	}

	@EventHandler
	public void on(InventoryOpenEvent e) {
		if (e.getInventory().getHolder() instanceof Menu) {

		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void on(AsyncPlayerChatEvent e) {
		IEssentials ess = (IEssentials) this.plugin.getServer().getPluginManager().getPlugin("Essentials");
		ess.getUser(e.getPlayer()).getConfigMap("pol");
		ess.getUser(e.getPlayer()).setConfigProperty("pol", "pol");
		String format = e.getFormat();
		if (e.getPlayer().hasPermission("пол.мужик")) {
			e.setFormat(format.replace("<пол>", "мужик"));
			return;
		}
		if (e.getPlayer().hasPermission("пол.баба")) {
			e.setFormat(format.replace("<пол>", "баба"));
			return;
		}
		e.setFormat(format.replace("<пол>", ""));
	}

	@EventHandler
	public void on(PlayerRespawnEvent e) {
		new SchedulerP(e.getPlayer(), this.plugin).runTask(this.plugin);
	}

	@EventHandler
	public void on(PlayerCommandPreprocessEvent e) {
		e.getPlayer().openInventory(this.plugin.fw.getInventory());
		/*
		 * String[] args = e.getMessage().split(" "); if (args.length == 2) { if
		 * (args[0].equals("/test")) { fawe.regen(e.getPlayer().getWorld(),
		 * args[1], false); } }
		 */
		// e.getPlayer().sendMessage(get(e.getMessage().split("
		// ")[0].replace("/", "")));
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
	public void on(ServerCommandEvent e) {
		if (get(e.getCommand().split(" ")[0].replace("/", "")).equalsIgnoreCase("say")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void on(InventoryClickEvent e) {
		NodeUtils.clickAction(e);
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

	/*
	 * @EventHandler public void on(PlayerInteractEvent e) { Block block =
	 * e.getClickedBlock(); Player player = e.getPlayer(); if (block.getType()
	 * == Material.CHEST) { sendBlockAction(block.getLocation(), player, 1);
	 * e.setCancelled(true); } }
	 */
	// Player player = e.getPlayer();
	// Location ploc = player.getLocation().clone().add(new Vector(0, 2,
	// 0));
	// try {
	// wg.test(e.getPlayer(), e.getPlayer().getWorld());
	// } catch (CommandException e1) {
	// e1.printStackTrace();
	// }
	// e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
	// TextComponent.fromLegacyText("text"));
	// ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)
	// SkullType.PLAYER.ordinal());
	// SkullMeta meta = (SkullMeta) head.getItemMeta();
	// meta.setOwningPlayer(e.getPlayer());
	// head.setItemMeta(meta);

	// List<ItemStack> stack = new ArrayList<ItemStack>();
	// stack.add(head);

	// Menu m = new Menu(stack, "Skull");
	// e.getPlayer().openInventory(m.getInventory());
	// }
	/*
	 * @EventHandler public void on(PlayerInteractEvent e) { Block block =
	 * e.getClickedBlock(); if(block.getType()==Material.CHEST) { Location loc =
	 * block.getLocation().add(new Vector(0,1,0)); Block bl = loc.getBlock();
	 * bl.setType(Material.CHEST); Chest chest1 = (Chest) block.getState();
	 * Chest chest2 = (Chest) bl.getState();
	 * chest2.getSnapshotInventory().setContents(chest1.getSnapshotInventory().
	 * getContents()); chest2.update(); } }
	 */

	@EventHandler
	public void on(RemoteServerCommandEvent e) {

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