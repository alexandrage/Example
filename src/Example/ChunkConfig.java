package Example;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Location;

public class ChunkConfig {
	private Map<String, LocationData> save = new HashMap<String, LocationData>();
	private Main plugin;

	ChunkConfig(Main plugin) {
		this.plugin = plugin;
	}

	public void add(Location loc, String player) {
		this.getChunk(loc).add(loc, player);
	}

	public void remove(Location loc) {
		this.getChunk(loc).remove(loc);
	}

	public boolean contains(Location loc) {
		return this.getChunk(loc).contains(loc);
	}

	public String get(Location loc) {
		return this.getChunk(loc).get(loc);
	}

	public LocationData getChunk(Location loc) {
		int ChunkX = loc.getBlockX() >> 4;
		int ChunkZ = loc.getBlockZ() >> 4;
		String s = ChunkX + "." + ChunkZ;
		if (save.containsKey(s)) {
			return save.get(s);
		} else {
			LocationData data = new LocationData();
			data.read(this.plugin.getDataFolder().getAbsolutePath(), s);
			save.put(s, data);
			return save.get(s);
		}
	}

	public void Save() {
		for (Entry<String, LocationData> tmp : save.entrySet()) {
			tmp.getValue().write(this.plugin.getDataFolder().getAbsolutePath(), tmp.getKey());
		}
		save.clear();
	}
}