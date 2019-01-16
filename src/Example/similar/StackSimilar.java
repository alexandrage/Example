package Example.similar;

import org.bukkit.inventory.ItemStack;

public class StackSimilar implements ISimilar {
	@Override
	public boolean isSimilar(ItemStack s1, ItemStack s2) {
		return s1.isSimilar(s2);
	}
}