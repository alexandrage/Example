package Example;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import Example.chest.Menu;
import Example.ench.FaceEnchantment;
import Example.event.CommandListener;
import Example.event.CommandRegister;
import Example.event.EventListener;
import Example.event.Packet;
import Example.gsoncfg.BanUtils;
import Example.gsoncfg.Bans;
import Example.runs.PScheduler;
import Example.sfg.ChunkConfig;
import Example.sfg.Configs;
import Example.sfg.MapSer;
import Example.sfg.StackList;
import net.ess3.api.IEssentials;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

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
	public FaceWorkbench fw;

	@Override
	public void onEnable() {
		// new Scheduler(this).runTaskTimer(this, 10, 10);
		new EventListener(this);
		fw = new FaceWorkbench(this);
		cfgs = new Configs();
		// shd = new Scheduler(this);
		// shd.runTaskTimer(this, 20, 20);
		Bans bans = BanUtils.setBans(this.getDataFolder().getAbsolutePath(), "ban.json");
		// tick = (ITickEvent)
		// getServer().getPluginManager().getPlugin("TickEvent");
		new PScheduler(ps).runTaskTimerAsynchronously(this, 20, 20);
		ess = (IEssentials) getServer().getPluginManager().getPlugin("Essentials");
		setupChat();
		setupEconomy();
		new Packet().hack(this);
		saves = new ChunkConfig(this);
		ench = new FaceEnchantment(120);
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		cfgs = new Configs();
		CommandRegister.reg(this, new CommandListener(this), new String[] { "example", "ex" }, "example",
				"/example reload");
		ItemStack stack = new ItemStack(Material.STONE);
		ItemStack istack = new ItemStack(Material.DIAMOND);
		Example.addRecipe(this, stack,
				new ItemStack[] { istack, istack, istack, istack, istack, istack, istack, istack, stack });
	}

	@Override
	public void onDisable() {
		cfgs.saveAll();
		saves.Save();
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