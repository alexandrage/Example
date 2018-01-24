package Example.sfg;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs(value = "MapSer")
public class MapSer implements ConfigurationSerializable {

	Map<String, Object> map;

	MapSer(Map<String, Object> map) {
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