package Example.fakechest;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Menu implements InventoryHolder {

	private Inventory inventory;

	public Menu(List<ItemStack> stack, String title) {
		this.inventory = Bukkit.createInventory(this, 9 * (int) Math.ceil((double) stack.size() / 9), title);
		for (int i = 0; i < stack.size(); i++) {
			inventory.setItem(i, stack.get(i));
		}
	}

	@Override
	public Inventory getInventory() {
		return this.inventory;
	}
}