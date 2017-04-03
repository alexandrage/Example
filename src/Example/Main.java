package Example;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getCommand("example").setExecutor(new CommandListener(this));
		new Scheduler(this).runTaskTimer(this, 0, 20);
		CustomConfig custom = new CustomConfig(this, "name");
		FileConfiguration cfg = custom.getCfg();
		cfg.set("test", "name");
		custom.saveCfg();
		custom.reloadCfg();
	}
}