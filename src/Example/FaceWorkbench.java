package Example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class FaceWorkbench implements InventoryHolder, Listener {
	private Inventory inventory;

	FaceWorkbench(Plugin plugin) {
		inventory = Bukkit.createInventory(this, InventoryType.WORKBENCH);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		ItemStack stack = new ItemStack(Material.STONE);
		inventory.addItem(stack);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@EventHandler
	public void on(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}
		if (e.getClickedInventory().getHolder() instanceof FaceWorkbench) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void on(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof FaceWorkbench) {
			if (e.getNewItems().containsKey(0) || e.getNewItems().containsKey(1) || e.getNewItems().containsKey(2)
					|| e.getNewItems().containsKey(3) || e.getNewItems().containsKey(4)
					|| e.getNewItems().containsKey(5) || e.getNewItems().containsKey(6)
					|| e.getNewItems().containsKey(7) || e.getNewItems().containsKey(8)
					|| e.getNewItems().containsKey(9)) {
				e.setCancelled(true);
			}
		}
	}
}