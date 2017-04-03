package Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs(value="Config")
public class Config implements ConfigurationSerializable{

	int i;
	String s;
	List<String> l;
	Map<String, String> m;

	public Integer getI() {
		return i;
	}

	public String getS() {
		return s;
	}

	public List<String> getL() {
		return l;
	}

	public Map<String, String> getM() {
		return m;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setS(String s) {
		this.s = s;
	}

	public void setL(List<String> l) {
		this.l = l;
	}

	public void setM(Map<String, String> m) {
		this.m = m;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", i);
		map.put("s", s);
		map.put("l", l);
		map.put("m", m);
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Config deserialize(Map<?, ?> map) {
		Config cfg = new Config();
		cfg.i = (int) map.get("i");
		cfg.s = (String) map.get("s");
		cfg.l = (List<String>) map.get("l");
		cfg.m = (Map<String, String>) map.get("m");
		return cfg;
	}
}
