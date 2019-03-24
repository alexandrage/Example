package Example.stack;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	ItemStack stack;

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

	public ItemBuilder setLore(String... name) {
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(Arrays.asList(name));
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setEnchantment(String... name) {
		ItemMeta meta = stack.getItemMeta();
		for (String e : name) {
			EnchantmentWrapper ench = new EnchantmentWrapper(e.split(":")[0]);
			meta.addEnchant(ench, Integer.parseInt(e.split(":")[1]), true);
		}
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setUnbreakable() {
		ItemMeta meta = stack.getItemMeta();
		meta.setUnbreakable(true);
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setAttribute(String... name) {
		ItemMeta meta = stack.getItemMeta();
		for (String a : name) {
			Attribute am = Attribute.valueOf(a.split(":")[0]);
			double d = Double.parseDouble(a.split(":")[1]);
			if (a.split(":").length == 3) {
				EquipmentSlot s = EquipmentSlot.valueOf(a.split(":")[2]);
				meta.addAttributeModifier(am,
						new AttributeModifier(UUID.randomUUID(), am.name(), d, Operation.ADD_NUMBER, s));
			} else {
				meta.addAttributeModifier(am, new AttributeModifier(am.name(), d, Operation.ADD_NUMBER));
			}
		}
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder hideAttributes() {
		ItemMeta meta = stack.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.stack.setItemMeta(meta);
		return this;
	}

	public ItemStack build() {
		return this.stack;
	}
}