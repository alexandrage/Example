package Example.event;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public class CommandRegister extends Command implements PluginIdentifiableCommand {
	protected Plugin plugin;
	protected final CommandExecutor owner;
	protected final Object registeredWith;

	public CommandRegister(String[] aliases, String desc, String usage, CommandExecutor owner, Object registeredWith,
			Plugin plugin2) {
		super(aliases[0], desc, usage, Arrays.asList(aliases));
		this.owner = owner;
		this.plugin = plugin2;
		this.registeredWith = registeredWith;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		return this.owner.onCommand(sender, this, label, args);
	}

	public Object getRegisteredWith() {
		return this.registeredWith;
	}

	public static void reg(Plugin plugin, CommandExecutor cxecutor, String[] aliases, String desc, String usage) {
		try {
			CommandRegister reg = new CommandRegister(aliases, desc, usage, cxecutor, new Object(), plugin);
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap map = (CommandMap) field.get(Bukkit.getServer());
			map.register(plugin.getDescription().getName(), reg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}