package Example;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventListener(this), this);
		getCommand("example").setExecutor(new CommandListener(this));
	}
}