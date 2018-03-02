package Example;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerExample implements Listener {
	Plugin plugin;

	public ListenerExample(Plugin plugin) {
		this.plugin = plugin;
	}
}