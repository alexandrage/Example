package Example;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Menu implements InventoryHolder {

	private Inventory inventory;
	private Map<Integer, Stack> list = new LinkedHashMap<Integer, Stack>();

	public Menu(StackList stack, String title) {
		int x = 0;
		for (Entry<String, Stack> st : stack.getMap().entrySet()) {
			list.put(x, st.getValue());
			x++;
		}
		this.inventory = Bukkit.createInventory(this, 9 * (int) Math.ceil((double) list.size() / 9), title);
		for (int i = 0; i < list.size(); i++) {
			inventory.setItem(i, list.get(i).getStack());
		}
	}

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

	public Stack getStack(int i) {
		return list.get(i);
	}

	public String getCommand(int i) {
		if (list.get(i) == null)
			return null;
		return list.get(i).getCommand();
	}
}