package Example;

import java.util.HashMap;
import java.util.Map;

public class Configs {
	private Map<String, CustomConfig> sfg = new HashMap<String, CustomConfig>();

	public CustomConfig getConfig(String name) {
		return sfg.get(name);
	}

	public Map<String, CustomConfig> getConfigs() {
		return sfg;
	}

	public void addConfig(Main pligin, String name) {
		CustomConfig custom = new CustomConfig(pligin, name);
		sfg.put(name, custom);
	}
}