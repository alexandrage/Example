package Example.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Example.Main;

public class CommandListener implements CommandExecutor {
	private Main plugin;

	public CommandListener(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String s = cmd.getName() + " " + String.join(" ", args);
		System.out.println(s);
		return true;
	}
}