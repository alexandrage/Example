package Example;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class EventListener implements Listener {
	private Main plugin;

	public EventListener(Main instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void on(EntityChangeBlockEvent e) {
		if (e.getEntityType().equals(EntityType.FALLING_BLOCK)) {
			e.setCancelled(true);
			e.getBlock().getState().update();
		}
	}

	@EventHandler
	public void on(PlayerInteractEvent e) {
		Menu menu = new Menu(this.plugin.stl, "shop");
		Inventory inv = menu.getInventory();
		e.getPlayer().openInventory(inv);
	}

	@EventHandler
	public void on(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof Menu && e.getClickedInventory()!=null) {
			e.setCancelled(true);
			InventoryHolder inv = e.getClickedInventory().getHolder();
			if(inv instanceof Menu) {
				System.out.println(e.getSlot());
				Menu m = (Menu) inv;
				System.out.println(m.getCommand(e.getSlot()));
			}
		}
	}
}