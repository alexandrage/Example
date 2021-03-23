package Example.stack;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	private ItemStack stack;

	public static ItemBuilder inst(Material m) {
		return new ItemBuilder(m);
	}

	public ItemBuilder(Material m) {
		this.stack = new ItemStack(m);
	}

	public ItemBuilder setName(String name) {
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		this.stack.setAmount(amount);
		return this;
	}

	public ItemBuilder addLore(String... value) {
		ItemMeta meta = this.stack.getItemMeta();
		List<String> lore = meta.getLore();
		for (String l : value) {
			lore.add(l);
		}
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder addEnchantment(EnchAdder... value) {
		ItemMeta meta = this.stack.getItemMeta();
		for (EnchAdder ench : value) {
			meta.addEnchant(ench.getEnchantment(), ench.getLevel(), true);
		}
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setUnbreakable() {
		ItemMeta meta = this.stack.getItemMeta();
		meta.setUnbreakable(true);
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setCustomModelData(int value) {
		ItemMeta meta = this.stack.getItemMeta();
		meta.setCustomModelData(value);
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder addAttribute(AttrAdder... attributes) {
		ItemMeta meta = this.stack.getItemMeta();
		for (AttrAdder attribute : attributes) {
			meta.addAttributeModifier(attribute.getAttribute(), attribute.getAttributeModifier());
		}
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder hideAttributes() {
		ItemMeta meta = this.stack.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemStack build() {
		return this.stack;
	}
}