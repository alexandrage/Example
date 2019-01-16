package Example.similar;

import org.bukkit.inventory.ItemStack;

public class NameSimilar implements ISimilar {
	@Override
	public boolean isSimilar(ItemStack s1, ItemStack s2) {
		boolean b1 = s1.getItemMeta().hasDisplayName();
		boolean b2 = s2.getItemMeta().hasDisplayName();
		if (b1 && b2) {
			String name1 = s1.getItemMeta().getDisplayName();
			String name2 = s2.getItemMeta().getDisplayName();
			return name1.equals(name2);
		}
		return false;
	}
}