package Example.cmd;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

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
		return this.owner.onCommand(sender, this, label, args);
	}

	public static void reg(Plugin plugin, CommandExecutor executor, String[] aliases, String desc, String usage) {
		try {
			CommandRegister reg = new CommandRegister(aliases, desc, usage, executor, plugin);
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap map = (CommandMap) field.get(Bukkit.getServer());
			map.register(plugin.getName(), reg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void clearCommand() {
		try {
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field2.setAccessible(true);
            Map<String, Command> command = (Map<String, Command>) field2.get(map);
            command.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Command> getCommand() {
		try {
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field2.setAccessible(true);
            return (Map<String, Command>) field2.get(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}