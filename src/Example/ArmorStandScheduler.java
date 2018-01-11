package Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorStandScheduler implements Runnable {
	private List<Location> circle;
	private Map<ArmorStand, Integer> tmp = new HashMap<ArmorStand, Integer>();
	private List<ItemStack> stacks;
	private int index = 0;

	ArmorStandScheduler(List<Location> circle, List<ItemStack> stacks) {
		this.circle = circle;
		this.stacks = stacks;
		for (int i = 0; i < circle.size(); i = i + 5) {
			spawn(i);
		}
	}

	@Override
	public void run() {
		int x = 0;
		long timer = 30l;
		for (;;) {
			x++;
			try {
				Thread.sleep(timer);
			} catch (Exception e) {
			}
			for (Entry<ArmorStand, Integer> tmp : tmp.entrySet()) {
				int i = tmp.getValue();
				Location loc = circle.get(i);
				CraftEntity craft = (CraftEntity) tmp.getKey();
				craft.getHandle().setPosition(loc.getX(), loc.getY(), loc.getZ());
				if (i == circle.size() - 1) {
					i = 0;
				} else {
					i++;
				}
				tmp.setValue(i);
				if (x == 1000) {
					craft.getPassenger().remove();
					craft.remove();
				}
			}
			if (x == 1000) {
				break;
			}
		}
	}

	private void spawn(int i) {
		Location l = circle.get(i);
		ItemStack stack = stacks.get(index);
		index = index + 1;
		Item it = Bukkit.getWorld(l.getWorld().getName()).dropItem(l, stack);
		ItemMeta meta = stack.getItemMeta();
		it.setCustomName(meta.getDisplayName());
		it.setCustomNameVisible(true);
		it.setGravity(false);
		it.setInvulnerable(true);
		it.setPickupDelay(Integer.MAX_VALUE);
		ArmorStand en = (ArmorStand) Bukkit.getWorld(l.getWorld().getName()).spawnEntity(l, EntityType.ARMOR_STAND);
		en.setGravity(false);
		en.setPassenger(it);
		en.setSmall(true);
		en.setVisible(false);
		en.setInvulnerable(true);
		tmp.put(en, i);
	}
}