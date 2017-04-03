package Example;

import java.util.HashMap;
import java.util.Map;

public class Configs {
	Map<String, CustomConfig> sfg = new HashMap<String, CustomConfig>();
	
	CustomConfig getConfig(String name) {
		return sfg.get(name);
	}
	
	void addConfig(Main pligin, String name) {
		CustomConfig custom = new CustomConfig(pligin, name);
		sfg.put(name, custom);
	}
}