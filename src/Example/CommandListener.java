package Example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {
	Main plugin;
	protected CommandListener(Main instance) {
		this.plugin =  instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		return true;
	}
}