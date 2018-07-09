package Example.runs;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class Scheduler extends BukkitRunnable {
	public static Map<String, Long> blocks = new ConcurrentHashMap<String, Long>();

	@Override
	public void run() {
		for (Entry<String, Long> block : blocks.entrySet()) {
			long time = block.getValue();
			String s = block.getKey();
			String[] spl = s.split(":");
			if (time == 100l) {
				Bukkit.getWorld(spl[0])
						.getBlockAt(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), Integer.parseInt(spl[3]))
						.setType(Material.BEDROCK);
			}
			if (time == 0l) {
				Bukkit.getWorld(spl[0])
						.getBlockAt(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), Integer.parseInt(spl[3]))
						.setTypeIdAndData(Integer.parseInt(spl[4]), (byte) Integer.parseInt(spl[5]), true);
				blocks.remove(block.getKey());
				continue;
			}
			blocks.put(s, time-1);
		}
	}
}