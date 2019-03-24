package Example.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import Example.Main;

public class EventListener implements Listener {
	private Main plugin;

	public EventListener(Main main) {
		this.plugin = main;
	}

	@EventHandler
	public void on(PlayerInteractEvent e) {

	}
}