package Example.event;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Example.Main;
import Example.sfg.CustomConfig;

public class CMDReload implements ICMD {
	private Main plugin;

	public CMDReload(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("example.reload")) {
			for (CustomConfig cfg : this.plugin.cfgs.getConfigs().values()) {
				cfg.reloadCfg();
			}
			sender.sendMessage("Example reloaded");
			Player p = (Player) sender;
			p.spawnParticle(Particle.LAVA, p.getLocation(), 5);
			return true;
		}
		return false;
	}
}