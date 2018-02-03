package Example.runs;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Scheduler extends BukkitRunnable {
	List<Player> ps;

	public Scheduler(List<Player> ps) {
		this.ps = ps;
	}

	@Override
	public void run() {
		for (Player p : ps) {
			runs(p);
		}
	}

	public void runs(Player p) {
		Location loc = p.getLocation().add(new Vector(0,0,-1.7));
		for (double t = 0; t < 2 * Math.PI; t += 0.1) {
			Location news = loc.add(0.2 * Math.cos(t), 0, 0.2 * Math.sin(t));
			p.spawnParticle(Particle.FLAME, news, 1, 0, 0, 0, 0);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}