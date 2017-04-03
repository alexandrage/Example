package Example;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public Configs cfgs;
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getCommand("example").setExecutor(new CommandListener(this));
		new Scheduler(this).runTaskTimer(this, 0, 20);
		cfgs = new Configs();
		cfgs.addConfig(this, "name1");
		cfgs.addConfig(this, "name2");
		CustomConfig cfg1 = cfgs.getConfig("name1");
		if(!cfg1.getCfg().contains("name1")) {
			cfg1.getCfg().set("name1", "value1");
			cfg1.saveCfg();
		}
		CustomConfig cfg2 = cfgs.getConfig("name2");
		if(!cfg1.getCfg().contains("name1")) {
			cfg2.getCfg().set("name2", "value2");
			cfg2.saveCfg();
		}
	}
}