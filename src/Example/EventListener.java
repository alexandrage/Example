package Example;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventListener implements Listener {
	Main plugin;
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
	  public void on(InventoryClickEvent e) {
		  e.setCancelled(true);
		  Player p = (Player)e.getWhoClicked();
		  p.updateInventory();
          p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
	  }
	  
}