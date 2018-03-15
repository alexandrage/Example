package Example;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import Example.sfg.CustomConfig;


public class ListenerExample implements Listener {
	Main plugin;

	public ListenerExample(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void on(PlayerQuitEvent e) {
		this.plugin.cmd.remove(e.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void on(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (this.plugin.cmd.contains(p.getName())) {
			this.plugin.cmd.add(this.plugin, p.getName(), false);
		}
		String msg = e.getMessage();
		CustomConfig cfg = this.plugin.cmd.get(p.getName());
		List<String> cmd = new ArrayList<String>();
		if (cfg.getCfg().contains("cmd")) {
			cmd = (List<String>) cfg.getCfg().getList("cmd");
		}
		if (cmd.contains(msg)) {
			// TODO
			e.setCancelled(true);
			return;
		}
		cmd.add(msg);
		cfg.getCfg().set("cmd", cmd);
		cfg.saveCfg();
	}
}