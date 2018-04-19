package Example.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import Example.Main;

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
		if (!this.plugin.cmds.containsKey(args[0])) {
			return false;
		}
		ICMD value = this.plugin.cmds.get(args[0]);
		return value.onCommand(sender, cmd, label, args);
	}
}