package Example.event;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Example.Main;
import Example.sfg.CustomConfig;

public class CommandListener implements CommandExecutor {
	private Main plugin;

	public CommandListener(JavaPlugin plugin) {
		this.plugin = (Main) plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (sender.hasPermission("example.reload") && args[0].equalsIgnoreCase("reload")) {
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