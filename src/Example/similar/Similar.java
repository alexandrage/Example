package Example.similar;

import org.bukkit.inventory.ItemStack;

public enum Similar {
	lore(new LoreSimilar()), stack(new StackSimilar()), name(new NameSimilar()), material(new MaterialSimilar());
	private ISimilar similar;

	private Similar(ISimilar similar) {
		this.similar = similar;
	}

	private boolean isSimilar(ItemStack s1, ItemStack s2) {
		return this.similar.isSimilar(s1, s2);
	}

	public static boolean has(ItemStack s1, ItemStack s2, Similar... similars) {
		boolean b = true;
		for(Similar similar : similars) {
			if(!similar.isSimilar(s2, s2)) {
				b=false;
			}
		}
		return b;
	}
}