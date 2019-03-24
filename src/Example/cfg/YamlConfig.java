package Example.cfg;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class YamlConfig<T> {
	private T t;
	private File file;

	public static boolean exist(JavaPlugin plugin, String name) {
		return new File(plugin.getDataFolder(), name + ".yml").exists();
	}

	public YamlConfig(JavaPlugin plugin, String name, T o) {
		this.t = o;
		this.file = new File(plugin.getDataFolder(), name + ".yml");
		load();
	}

	//TODO  Тест.
	public YamlConfig(String name, T o) {
		this.t = o;
		this.file = new File(name + ".yml");
		load();
	}

	private void load() {
		try {
			YamlReader reader = new YamlReader(new FileReader(this.file));
			this.t = (T) reader.read(this.t.getClass());
		} catch (Exception e) {

		}
	}

	public void save() {
		try {
			YamlWriter writer = new YamlWriter(new FileWriter(this.file));
			writer.getConfig().writeConfig.setWriteRootTags(false);
			writer.write(this.t);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public T get() {
		return this.t;
	}
}