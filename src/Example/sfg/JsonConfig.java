package Example.sfg;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConfig<T> {
	private File file;
	private Gson gson = new Gson();
	private T t;

	public static boolean exist(JavaPlugin plugin, String name) {
		return new File(plugin.getDataFolder(), name + ".yml").exists();
	}

	public JsonConfig(JavaPlugin plugin, String name, T o) {
		this.t = o;
		this.file = new File(plugin.getDataFolder(), name + ".yml");
		this.file.getParentFile().mkdirs();
		this.load();
	}

	private void load() {
		String s = "{}";
		try {
			s = FileUtils.readFileToString(this.file, Charset.defaultCharset());
		} catch (IOException e) {

		}
		t = (T) gson.fromJson(s, this.t.getClass());
	}

	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileUtils.writeStringToFile(this.file, gson.toJson(this.t), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public T get() {
		return this.t;
	}

	public String toString() {
		return t.toString();
	}
}