package Example.event;

import java.lang.reflect.Field;
//import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
//import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
//import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.sk89q.minecraft.util.commands.CommandException;

import Example.wg;
import Example.runs.ArmorStandScheduler;

//import net.minecraft.server.v1_12_R1.BlockPosition;
//import net.minecraft.server.v1_12_R1.ContainerWorkbench;

public class EventListener implements Listener {
	// TODO
	private Plugin plugin;

	public EventListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onTab(TabCompleteEvent e) {
		List<String> tmp = e.getCompletions();
		if(!tmp.contains("Admin")) {
			tmp.add("Admin");
		}
		if(!tmp.contains("zenit_")) {
			tmp.add("zenit_");
		}
		Collections.sort(tmp);
		e.setCompletions(tmp);
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		try {
			Chunk chunk = e.getClickedBlock().getChunk();
			wg.get(player, player.getName()+chunk.getX()+chunk.getZ(), player.getWorld());
			player.sendMessage("Ок");
		} catch (CommandException ex) {
			player.sendMessage(ex.getMessage());
		}
	}
	
	@EventHandler
	public void on(PlayerCommandPreprocessEvent e) {
		
		/*
		 * player.sendMessage("Hello, Hello world"); try { wg.get(player,
		 * "rg_id", player.getWorld()); } catch (CommandException ex) {
		 * player.sendMessage(ex.getMessage()); }
		 */
	}

	/*
	 * @EventHandler public void onPlayerCraft(CraftItemEvent e) throws
	 * Exception { if (e.getInventory().getType() == InventoryType.WORKBENCH) {
	 * CraftInventoryView inv = (CraftInventoryView) e.getView(); BlockPosition
	 * bp = (BlockPosition) field.get(inv.getHandle());
	 * System.out.println(bp.getX() + " " + bp.getY() + " " + bp.getZ()); } }
	 * 
	 * static Field field = null; static { try { field =
	 * ContainerWorkbench.class.getDeclaredField("h");
	 * field.setAccessible(true); } catch (Exception e) { e.printStackTrace(); }
	 * }
	 */

	/*
	 * @EventHandler public void on(PlayerInteractEvent e) throws Exception {
	 * e.setCancelled(true); Block block = e.getClickedBlock(); Location loc =
	 * block.getLocation(); Method method =
	 * CraftBlock.class.getDeclaredMethod("getNMSBlock");
	 * method.setAccessible(true); net.minecraft.server.v1_12_R1.Block b =
	 * (net.minecraft.server.v1_12_R1.Block)method.invoke(block);
	 * SoundEffectType soundeffecttype = b.getStepSound(); CraftWorld w =
	 * (CraftWorld) loc.getWorld(); net.minecraft.server.v1_12_R1.World world =
	 * w.getHandle(); Field field = SoundEffectType.class.getDeclaredField("o");
	 * field.setAccessible(true); Field modifiersField =
	 * Field.class.getDeclaredField("modifiers");
	 * modifiersField.setAccessible(true); modifiersField.setInt(field,
	 * field.getModifiers() & ~Modifier.FINAL); SoundEffect se = (SoundEffect)
	 * field.get(soundeffecttype); world.a(null, loc.getX(), loc.getY(),
	 * loc.getZ(), se, SoundCategory.NEUTRAL, soundeffecttype.a(), 0.8f); }
	 */

	@EventHandler
	public void onCom(ServerCommandEvent e) {

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