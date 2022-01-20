package Example.cfg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.plugin.Plugin;

public class Configs {
	private Map<String, CustomConfig> cfg = new ConcurrentHashMap<String, CustomConfig>();
	private Plugin plugin;

	public Configs(Plugin plugin) {
		this.plugin = plugin;
	}

	public CustomConfig get(String name) {
		if (this.cfg.containsKey(name)) {
			add(name);
		}
		return this.cfg.get(name);
	}

	public Map<String, CustomConfig> getConfigs() {
		return this.cfg;
	}

	public CustomConfig add(String name) {
		CustomConfig custom = new CustomConfig(name, this.plugin);
		this.cfg.put(name, custom);
		return this.cfg.get(name);
	}

	public void save(String name) {
		if (this.cfg.containsKey(name)) {
			add(name);
		}
		this.cfg.get(name).save();
		this.cfg.remove(name);
	}

	public void saveAll() {
		for (String name : this.cfg.keySet()) {
			save(name);
		}
	}
}