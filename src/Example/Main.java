package Example;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public Configs cfgs;
	public StackList stl;
	public Menu menu;
	public FaceEnchantment ench;
	public ChunkConfig saves;

	@Override
	public void onEnable() {
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
		CommandRegister.reg(this, new CommandListener(this), new String[] { "example", "ex" }, "example", "example");
		ItemStack stack = new ItemStack(Material.STONE);
		Example.addRecipe(this, stack, new ItemStack[]{stack,stack,stack,stack,stack,stack,stack,stack,stack});
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
}