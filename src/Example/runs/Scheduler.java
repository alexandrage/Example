package Example.runs;

import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import Example.Main;
import Example.sfg.CustomConfig;

public class Scheduler extends BukkitRunnable {
	Main plugin;

	public Scheduler(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		Map<String, CustomConfig> cfg = plugin.cfgs.getConfigs();
		for (Entry<String, CustomConfig> name : cfg.entrySet()) {
			Player p = Bukkit.getPlayerExact(name.getKey());
			if (p == null) {
				continue;
			}
			if (plugin.cfgs.get(p.getName()).getCfg().getBoolean("heal", false) == true
					&& (p.getHealth() < p.getHealthScale())) {
				double i = p.getHealth() + 1;
				if (i > 20) {
					i = 20;
				}
				p.setHealth(i);
			} else {
				plugin.cfgs.get(p.getName()).getCfg().set("heal", false);
				plugin.cfgs.remove(p.getName());
			}
		}
	}
}