package Example.stack;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StackList {
	private Map<String, Stack> stack = new LinkedHashMap<String, Stack>();

	public StackList(FileConfiguration cfgs) {
		for (String cfg : cfgs.getKeys(false)) {
			stack.put(cfg, new Stack(cfgs.getConfigurationSection(cfg)));
		}
	}

	public Map<String, Stack> getMap() {
		return stack;
	}

	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}