package Example.sfg;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.plugin.java.JavaPlugin;

public class Configs {
	private Map<String, CustomConfig> sfg = new HashMap<String, CustomConfig>();

	public CustomConfig get(String name) {
		return sfg.get(name);
	}
	
	public CustomConfig getLoad(JavaPlugin plugin, String name, boolean isResource) {
		if(!sfg.containsKey(name)) {
			this.add(plugin, name, isResource);
		}
		return sfg.get(name);
	}

	public Map<String, CustomConfig> getConfigs() {
		return sfg;
	}

	public void add(JavaPlugin plugin, String name, boolean isResource) {
		CustomConfig custom = new CustomConfig(plugin, name, isResource);
		sfg.put(name, custom);
	}

	public void remove(String name) {
		if (contains(name)) {
			sfg.get(name).saveCfg();
			sfg.remove(name);
		}
	}

	public boolean contains(String name) {
		return sfg.containsKey(name);
	}

	public void saveAll() {
		for (Entry<String, CustomConfig> s : sfg.entrySet()) {
			sfg.get(s.getKey()).saveCfg();
			sfg.remove(s.getKey());
		}
	}
}