package Example.cmd;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandRegister extends Command implements PluginIdentifiableCommand {
	protected Plugin plugin;
	protected final CommandExecutor owner;

	public CommandRegister(String[] aliases, String desc, String usage, CommandExecutor owner, Plugin plugin) {
		super(aliases[0], desc, usage, Arrays.asList(aliases));
		this.owner = owner;
		this.plugin = plugin;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!this.testPermission(sender)) {
			return true;
		}

		if (this.owner.onCommand(sender, this, label, args)) {
			return true;
		} else {
			sender.sendMessage(this.usageMessage);
			return false;
		}
	}

	public static void register(Plugin plugin, CommandExecutor executor, String[] aliases, String desc, String usage) {
		CommandRegister reg = new CommandRegister(aliases, desc, usage, executor, plugin);
		CommandMap map = Bukkit.getCommandMap();
		map.register(plugin.getName(), reg);
		syncCommands();
	}

	public static void unregister(String name) {
		try {
			Map<String, Command> command = Bukkit.getCommandMap().getKnownCommands();
			Command cmd = command.get(name);
			InputStream stream = cmd.getClass().getResourceAsStream("/plugin.yml");
			PluginDescriptionFile desc = new PluginDescriptionFile(stream);
			command.remove(cmd.getName());
			command.remove(desc.getName().toLowerCase() + ":" + cmd.getName());
			for (String aliases : cmd.getAliases()) {
				command.remove(aliases);
				command.remove(desc.getName().toLowerCase() + ":" + aliases);
			}
			syncCommands();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void syncCommands() {
		String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
		try {
			Class<?> server = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
			Method syncCommands = server.getDeclaredMethod("syncCommands");
			syncCommands.setAccessible(true);
			syncCommands.invoke(Bukkit.getServer());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}