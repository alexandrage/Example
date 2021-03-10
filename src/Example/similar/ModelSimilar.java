package Example.similar;

import org.bukkit.inventory.ItemStack;

public class ModelSimilar implements ISimilar {
	@Override
	public boolean isSimilar(ItemStack s1, ItemStack s2) {
		boolean b1 = s1.getItemMeta().hasCustomModelData();
		boolean b2 = s2.getItemMeta().hasCustomModelData();
		if (b1 && b2) {
			int model1 = s1.getItemMeta().getCustomModelData();
			int model2 = s2.getItemMeta().getCustomModelData();
			return model1 == model2;
		}
		return false;
	}
}