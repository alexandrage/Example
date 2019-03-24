package Example.cfg;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CustomConfig {

	private YamlConfiguration yml;
	private File file;

	CustomConfig(String name, Plugin plugin) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		try {
			this.yml.load(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean exist(String name, Plugin plugin) {
		File file = new File(plugin.getDataFolder(), name + ".yml");
		return file.exists();
	}
}