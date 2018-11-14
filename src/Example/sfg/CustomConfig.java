package Example.sfg;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig {

	private YamlConfiguration yml;
	private File file;

	public CustomConfig(JavaPlugin plugin, String name, boolean isResource) {
		this.file = new File(plugin.getDataFolder(), name + ".yml");
		this.file.getParentFile().mkdirs();
		if (!this.file.exists() && isResource) {
			plugin.saveResource(name + ".yml", false);
		}
		yml = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getCfg() {
		return yml;
	}

	public void saveCfg() {
		try {
			yml.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadCfg() {
		try {
			yml.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}