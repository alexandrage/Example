package Example.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Scheduler extends BukkitRunnable {
	int maxTime;
	int time;
	public Scheduler(int i) {
		maxTime = i;
	}

	@Override
	public void run() {
		if(Bukkit.getOnlinePlayers().size()==0) {
			this.time++;
		} else {
			this.time = 0;
		}
		if(this.time==maxTime) {
			Bukkit.shutdown();
		}
	}
}