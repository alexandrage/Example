package Example.cfg;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CustomConfig {

	private YamlConfiguration yml;
	private File file;

	public CustomConfig(String name, Plugin plugin) {
		this.file = new File(plugin.getDataFolder(), name + ".yml");
		this.yml = YamlConfiguration.loadConfiguration(this.file);
		this.file.getParentFile().mkdirs();
	}

	public FileConfiguration get() {
		return this.yml;
	}

	public void save() {
		try {
			this.yml.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		try {
			this.yml.load(this.file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static boolean exist(String name, Plugin plugin) {
		return new File(plugin.getDataFolder(), name + ".yml").exists();
	}
}