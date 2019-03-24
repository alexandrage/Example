package Example.cfg;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs(value = "MapSer")
public class MapSer implements ConfigurationSerializable {

	private Map<String, Object> map;

	public MapSer(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public Map<String, Object> serialize() {
		return map;
	}

	public static MapSer deserialize(Map<String, Object> map) {
		return new MapSer(map);
	}

	public Map<String, Object> get() {
		return map;
	}
}