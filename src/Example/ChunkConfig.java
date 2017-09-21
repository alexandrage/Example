package Example;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.bukkit.Location;

import com.google.gson.Gson;

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
		String s = ChunkX + "." + ChunkZ + ".json";
		if (save.containsKey(s)) {
			return save.get(s);
		} else {
			String str = "{}";
			Gson gson = new Gson();
			try {
				File file = new File(this.plugin.getDataFolder(), s);
				if (!file.exists()) {
					FileUtils.writeStringToFile(file, new LocationData().toString(), Charset.defaultCharset());
				}
				str = FileUtils.readFileToString(file, Charset.defaultCharset());
			} catch (IOException e) {
				e.printStackTrace();
			}
			save.put(s, gson.fromJson(str, LocationData.class));
			return save.get(s);
		}
	}

	public void Save() {
		for (Entry<String, LocationData> tmp : save.entrySet()) {
			File file = new File(this.plugin.getDataFolder(), tmp.getKey());
			try {
				FileUtils.writeStringToFile(file, tmp.getValue().toString(), Charset.defaultCharset());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}