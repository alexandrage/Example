package Example.similar;

import java.util.List;
import org.bukkit.inventory.ItemStack;

public class LoreSimilar implements ISimilar {
	@Override
	public boolean isSimilar(ItemStack s1, ItemStack s2) {
		boolean b1 = s1.getItemMeta().hasLore();
		boolean b2 = s2.getItemMeta().hasLore();
		if (b1 && b2) {
			List<String> lore1 = s1.getItemMeta().getLore();
			List<String> lore2 = s2.getItemMeta().getLore();
			return lore1.equals(lore2);
		}
		return false;
	}
}