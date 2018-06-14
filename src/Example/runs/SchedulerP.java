package Example.runs;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import Example.Main;
import Example.sfg.CustomConfig;

public class SchedulerP extends BukkitRunnable {
	private Player p;
	Main plugin;

	public SchedulerP(Player p, Main plugin) {
		this.p = p;
		this.plugin = plugin;
	}

	@Override
	public void run() {
		p.setHealth(0.5);
		this.plugin.cfgs.add(this.plugin, p.getName(), false);
		CustomConfig cfg = this.plugin.cfgs.get( p.getName());
		cfg.getCfg().set("heal", true);
	}
}