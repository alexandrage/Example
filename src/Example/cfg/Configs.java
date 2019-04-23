package Example.cfg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.plugin.Plugin;

public class Configs {
	private Map<String, CustomConfig> sfg = new ConcurrentHashMap<String, CustomConfig>();
	private Plugin plugin;

	public Configs(Plugin plugin) {
		this.plugin = plugin;
	}

	public CustomConfig get(String name) {
		if (this.sfg.get(name) == null)
			add(name);
		return this.sfg.get(name);
	}

	public Map<String, CustomConfig> getConfigs() {
		return this.sfg;
	}

	public void add(String name) {
		CustomConfig custom = new CustomConfig(name, this.plugin);
		this.sfg.put(name, custom);
	}

	public void save(String name) {
		if (this.sfg.get(name) == null)
			add(name);
		try {
			this.sfg.get(name).save();
			this.sfg.remove(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAll() {
		for (String name : this.sfg.keySet()) {
			save(name);
		}
	}
}