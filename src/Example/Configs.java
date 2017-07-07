package Example;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public class Configs {
	private Map<String, CustomConfig> sfg = new HashMap<String, CustomConfig>();

	public CustomConfig get(String name) {
		return sfg.get(name);
	}

	public Map<String, CustomConfig> getConfigs() {
		return sfg;
	}

	public void add(JavaPlugin plugin, String name) {
		CustomConfig custom = new CustomConfig(plugin, name);
		sfg.put(name, custom);
	}
}
