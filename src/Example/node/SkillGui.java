package Example.node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkillGui implements InventoryHolder {
	private Inventory inventory;
	private Map<Integer, Data> data = new HashMap<Integer, Data>();

	public SkillGui(String title) {
		this.inventory = Bukkit.createInventory(this, 54, title);
	}

	@Override
	public Inventory getInventory() {
		return this.inventory;
	}

	public <T> void setItem(Node<T> node) {
		Data d = (Data) node.getData();
		String name = d.getName();
		Integer level = d.getLevel();
		Integer max = d.getMaxLevel();
		Integer index = d.getIndex();
		if (node.getParent() != null) {
			Data dp = (Data) node.getParent().getData();
			if (dp.getLevel() < dp.getNext()) {
				return;
			}
		}
		ItemStack item = new ItemStack(Material.STONE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(new String[] { "level: " + level + "/" + max }));
		item.setItemMeta(meta);
		data.put(index, d);
		this.getInventory().setItem(index, item);
		node.getChildren().forEach(each -> setItem(each));
	}
	
	public Data getData(int index) {
		return data.get(index);	
	}

	public void clearItems() {
		this.getInventory().clear();
	}
}