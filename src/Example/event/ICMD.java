package Example.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICMD {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);
}