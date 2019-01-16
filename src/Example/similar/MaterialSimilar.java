package Example.similar;

import org.bukkit.inventory.ItemStack;

public class MaterialSimilar implements ISimilar {
	@Override
	public boolean isSimilar(ItemStack s1, ItemStack s2) {
		return s1.getType() == s2.getType();
	}
}