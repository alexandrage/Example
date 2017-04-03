package Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getCommand("example").setExecutor(new CommandListener(this));
		new Scheduler(this).runTaskTimer(this, 0, 20);
		if(!getConfig().contains("example")) {
			Config cfg = new Config();
			cfg.setI(10);
			cfg.setS("string");
			cfg.setL(Arrays.asList(new String[]{"one", "two"}));
			Map<String, String> m = new HashMap<String, String>();
			m.put("one", "on");
			m.put("two", "tw");
			cfg.setM(m);
			getConfig().set("example", cfg);
			saveConfig();
		}
	}

	static {
    	ConfigurationSerialization.registerClass(Config.class, "Example");
	}
}