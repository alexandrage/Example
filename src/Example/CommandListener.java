package Example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {
	private Main plugin;
	protected CommandListener(Main instance) {
		this.plugin =  instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if(arg.length == 0) return false;
		if(sender.hasPermission("example.reload") && arg[0].equalsIgnoreCase("reload")) {
			for(CustomConfig cfg : this.plugin.cfgs.getConfigs().values()) {
				cfg.reloadCfg();
			}
		}
		return true;
	}
}