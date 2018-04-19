package Example.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import com.sk89q.minecraft.util.commands.CommandException;

import Example.Example;
import Example.Main;
import Example.wg;
import Example.fakechest.Menu;
import Example.nms.NBTExample;
import Example.node.Data;
import Example.node.Node;
import Example.node.NodeUtils;
import Example.node.SkillData;
import Example.node.SkillGui;
import Example.runs.ArmorStandScheduler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class EventListener implements Listener {
	private Main plugin;

	public EventListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void on(PlayerCommandPreprocessEvent e) {
		// TODO test.
		Map<String, SkillData> skill = new HashMap<String, SkillData>();
		NodeUtils.skills.put(e.getPlayer(), skill);
		NodeUtils.setupGui(e.getPlayer());
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

	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Location ploc = player.getLocation().clone().add(new Vector(0, 2, 0));
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
	}
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
	public void on(ServerCommandEvent e) {

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