package Example.stack;

import org.bukkit.enchantments.Enchantment;

public class EnchAdder {
	private Enchantment enchantment;
	private int level;

	public EnchAdder(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}

	public Enchantment getEnchantment() {
		return this.enchantment;
	}

	public int getLevel() {
		return this.level;
	}
}