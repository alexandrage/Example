package Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import Example.chest.Menu;
import Example.ench.FaceEnchantment;
import Example.event.CMDReload;
import Example.event.CommandListener;
import Example.event.CommandRegister;
import Example.event.EventListener;
import Example.event.ICMD;
import Example.event.Packet;
import Example.runs.Scheduler;
import Example.sfg.ChunkConfig;
import Example.sfg.Configs;
import Example.sfg.CustomConfig;
import Example.sfg.MapSer;
import Example.sfg.StackList;
import net.ess3.api.IEssentials;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import tick.ITickEvent;

public class Main extends JavaPlugin {
	public static Chat chat = null;
	public static Economy economy = null;
	public Configs cfgs;
	public StackList stl;
	public Menu menu;
	public FaceEnchantment ench;
	public ChunkConfig saves;
	public IEssentials ess = null;
	public List<Player> ps = new ArrayList<Player>();
	public ITickEvent tick;
	public Map<String, ICMD> cmds = new HashMap<String, ICMD>();

	@Override
	public void onEnable() {
		tick = (ITickEvent) getServer().getPluginManager().getPlugin("TickEvent");
		new Scheduler(ps).runTaskTimerAsynchronously(this, 20, 20);
		ess = (IEssentials) getServer().getPluginManager().getPlugin("Essentials");
		setupChat();
		setupEconomy();
		new Packet().hack(this);
		Bukkit.getLogger().getHandlers();
		saves = new ChunkConfig(this);
		ench = new FaceEnchantment(120);
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		cfgs = new Configs();
		cfgs.add(this, "stack", true);
		CustomConfig cfg = cfgs.get("stack");
		stl = new StackList(cfg.getCfg());
		menu = new Menu(this.stl, "gui");
		cfgs.add(this, "name1", false);
		cfgs.add(this, "name2", false);
		CustomConfig cfg1 = cfgs.get("name1");
		if (!cfg1.getCfg().contains("name1")) {
			cfg1.getCfg().set("name1", "value1");
			cfg1.saveCfg();
		}
		CustomConfig cfg2 = cfgs.get("name2");
		if (!cfg1.getCfg().contains("name2")) {
			cfg2.getCfg().set("name2", "value2");
			cfg2.saveCfg();
		}
		cmds.put("reload", new CMDReload(this));
		CommandRegister.reg(this, new CommandListener(this), new String[] { "example", "ex" }, "example",
				"/example reload");
		ItemStack stack = new ItemStack(Material.STONE);
		ItemStack istack = new ItemStack(Material.DIAMOND);
		Example.addRecipe(this, stack,
				new ItemStack[] { istack, istack, istack, istack, istack, istack, istack, istack, stack });
	}

	@Override
	public void onDisable() {
		saves.Save();
	}

	void set(FileConfiguration cfg, Map<String, Integer> map) {
		cfg.set("keys", map);
	}

	Map<String, Integer> get(FileConfiguration cfg) {
		ConfigurationSection cs = this.getConfig().getConfigurationSection("keys");
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String tmp : cs.getKeys(false)) {
			map.put(tmp, cs.getInt(tmp));
		}
		return map;
	}

	static {
		ConfigurationSerialization.registerClass(MapSer.class, "MapSer");
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = (Chat) chatProvider.getProvider();
		}
		return chat != null;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}
}