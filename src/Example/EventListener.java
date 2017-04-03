package Example;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

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
}