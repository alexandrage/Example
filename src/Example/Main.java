package Example;

import org.bukkit.plugin.java.JavaPlugin;

import Example.cmd.CommandListener;
import Example.events.EventListener;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
		this.getCommand("commanName").setExecutor(new CommandListener());
	}
}