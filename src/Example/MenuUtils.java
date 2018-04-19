package Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuUtils {

	public static Menu subMenu(List<List<ItemStack>> lists, int i, String name, ItemStack next) {
		if (i > lists.size() || i < 1) {
			i = 1;
		}
		List<ItemStack> stack = new ArrayList<ItemStack>(lists.get(i - 1));
		ItemMeta meta = next.getItemMeta();
		meta.setDisplayName("Next");
		meta.setLore(Arrays.asList(new String[] { String.valueOf(i) }));
		next.setItemMeta(meta);
		stack.add(next);
		return new Menu(stack, name);
	}

	public static void menu(InventoryClickEvent e, List<List<ItemStack>> lists) {
		if (e.getInventory().getHolder() instanceof Menu) {
			e.setCancelled(true);
			ItemStack item = e.getCurrentItem();
			if (item == null)
				return;
			ItemMeta meta = item.getItemMeta();
			if (meta == null)
				return;
			if (!meta.hasDisplayName())
				return;
			if (!meta.hasLore())
				return;
			if (meta.getDisplayName().equals("Next")) {
				ItemStack next = new ItemStack(Material.GLASS, 1);
				int cur = Integer.parseInt(meta.getLore().get(0));
				Menu menu = subMenu(lists, cur + 1, "Menu", next);
				e.getWhoClicked().openInventory(menu.getInventory());
			}
		}
	}

	public static void menu(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof Menu) {
			e.setCancelled(true);
		}
	}

	public static void menu(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof Menu) {
			((Player) e.getPlayer()).updateInventory();
		}
	}

	public static <T> List<List<T>> split(List<T> list, int targetSize) {
		List<List<T>> lists = new ArrayList<List<T>>();
		for (int i = 0; i < list.size(); i += targetSize) {
			lists.add(list.subList(i, Math.min(i + targetSize, list.size())));
		}
		return lists;
	}
}