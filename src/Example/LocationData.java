package Example;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LocationData {
	private Map<String, String> locs = new HashMap<String, String>();

	private String convert(Location loc) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		int tmpx = x >> 4;
		int tmpx2 = tmpx << 4;
		int tmpz = z >> 4;
		int tmpz2 = tmpz << 4;
		return (x - tmpx2) + "." + y + "." + (z - tmpz2);
	}

	public void add(Location loc, String player) {
		locs.put(convert(loc), player);
	}

	public void remove(Location loc) {
		locs.remove(convert(loc));
	}

	public boolean contains(Location loc) {
		return locs.containsKey(convert(loc));
	}

	public String get(Location loc) {
		if (contains(loc)) {
			return locs.get(convert(loc));
		}
		return null;
	}

	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}