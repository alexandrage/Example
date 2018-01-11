package Example;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandListener implements CommandExecutor {
	private Main plugin;

	protected CommandListener(JavaPlugin plugin) {
		this.plugin = (Main) plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equalsIgnoreCase("reload")) {
			return new Reload(sender, cmd, label, args).onCommand();
		}
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
		if (args[0].equalsIgnoreCase("info")) {
			ConversationFactory factory = this.plugin.factory;
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("data", "first");
			Conversation conv = factory.withFirstPrompt(new TestPrompt()).withPrefix(new CP())
					.withInitialSessionData(map).withLocalEcho(false).buildConversation((Conversable) sender);
			conv.addConversationAbandonedListener(new CA());
			conv.begin();
			return true;
		}
		return false;
	}
}