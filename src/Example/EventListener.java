package Example;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class EventListener implements Listener {
	private Main plugin;

	public EventListener(Main instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void on(BlockPlaceEvent e) {
		Location loc = e.getBlockPlaced().getLocation();
		this.plugin.saves.add(loc, e.getPlayer().getName());
	}

	@EventHandler
	public void on(BlockBreakEvent e) {
		Location loc = e.getBlock().getLocation();
		e.getPlayer().sendMessage(this.plugin.saves.contains(loc) + "");
		e.getPlayer().sendMessage(this.plugin.saves.get(loc));
		this.plugin.saves.remove(loc);
	}
}