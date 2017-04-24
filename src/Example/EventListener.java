package Example;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
		e.getPlayer().sendMessage(this.plugin.cfgs.get("name1").getCfg().get("name1")+"");
		e.getPlayer().sendMessage(this.plugin.cfgs.get("name2").getCfg().get("name2")+"");
	}
	  
}