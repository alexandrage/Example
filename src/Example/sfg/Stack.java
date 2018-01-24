package Example.sfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Stack {
	private String material;
	private int amount;
	private int date;
	private String customname;
	private List<String> lore;
	private Map<String, Integer> enchant;
	private List<String> flags;
	private boolean unbreak;
	private String command;

	@SuppressWarnings("unchecked")
	public Stack(ConfigurationSection config) {
		this.material = config.getString("material");
		this.amount = config.getInt("amount");
		this.date = config.getInt("date");
		this.customname = config.getString("customname");
		this.lore = (List<String>) config.get("lore");
		for (Map<?, ?> enchs : config.getMapList("enchant")) {
			for (Entry<?, ?> ench : enchs.entrySet()) {
				enchant = new HashMap<String, Integer>();
				enchant.put((String) ench.getKey(), (int) ench.getValue());
			}
		}
		this.flags = (List<String>) config.get("flags");
		this.unbreak = config.getBoolean("unbreak");
		this.command = config.getString("command");
	}

	public ItemStack getStack() {
		ItemStack stack = new ItemStack(Material.valueOf(material.toUpperCase()), amount, (short) date);
		ItemMeta meta = stack.getItemMeta();
		if (customname != null) {
			meta.setDisplayName(customname);
		}
		if (lore != null) {
			meta.setLore(lore);
		}
		if (enchant != null) {
			for (String ench : enchant.keySet()) {
				meta.addEnchant(Enchantment.getByName(ench.toUpperCase()), enchant.get(ench), true);
			}
		}
		if (flags != null) {
			for (String flag : flags) {
				Set<ItemFlag> f = meta.getItemFlags();
				f.add(ItemFlag.valueOf(flag));
			}
		}
		if (unbreak) {
			meta.setUnbreakable(true);
		}
		stack.setItemMeta(meta);
		return stack;
	}

	public String getCommand() {
		return this.command;
	}

	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}