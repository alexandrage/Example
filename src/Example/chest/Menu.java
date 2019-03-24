package Example.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import Example.stack.Stack;

public class Menu implements InventoryHolder {

	private Inventory inventory;
	private List<Stack> stack;

	public Menu(String title) {
		this.inventory = Bukkit.createInventory(this, 9, title);
		this.stack = new ArrayList<Stack>();
	}

	@Override
	public Inventory getInventory() {
		return this.inventory;
	}

	public void addItem(Stack stack) {
		this.inventory.addItem(stack.getStack());
		this.stack.add(stack);
	}

	public Stack getItem(int index) {
		return this.stack.get(index);
	}
}