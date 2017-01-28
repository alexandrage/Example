package Example;

import java.io.IOException;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.SpawnEgg;

public class EventListener implements Listener {
	Main plugin;
	public EventListener(Main instance) {
		this.plugin = instance;
	}
	
    @EventHandler
    public void on(EntityChangeBlockEvent e) {
        if (e.getEntityType().equals(EntityType.FALLING_BLOCK)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void on(PlayerInteractEvent e) {
		SpawnEgg egg = new SpawnEgg(EntityType.HORSE);
        e.getPlayer().setItemInHand(egg.toItemStack());
    }
}
