package Example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import Example.similar.Similar;

public class Example {
	public static boolean checkremove(Player p, ItemStack s, int c) {
		if (calc(p, s) >= c) {
			clear(p, s, c);
			return true;
		}
		return false;
	}

	public static int calc(Player p, ItemStack s) {
		int count = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if (stack == null)
				continue;
			if (Similar.has(stack, s, Similar.name, Similar.material)) {
				count += stack.getAmount();
			}
		}
		return count;
	}

	public static void clear(Player p, ItemStack s, int c) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			ItemStack stack = p.getInventory().getItem(i);
			if (stack == null)
				continue;
			if (Similar.has(stack, s, Similar.name, Similar.material)) {
				if (stack.getAmount() == 0)
					break;
				if (stack.getAmount() <= c) {
					c = c - stack.getAmount();
					stack.setAmount(-1);
				}
				if (stack.getAmount() > c) {
					stack.setAmount(stack.getAmount() - c);
					c = 0;
				}
			}
		}
	}

	public static void spawnP(World world, Location location, int count, double dx, double dz, double dy, double speed,
			Color color, float size) {
		DustOptions dustOptions = new DustOptions(color, size);
		world.spawnParticle(Particle.REDSTONE, location, count, dx, dy, dz, speed, dustOptions);
	}

	public static void addRecipe(ItemStack[] stacks, ItemStack stack) {
		ShapedRecipe rc = new ShapedRecipe(NamespacedKey.minecraft(UUID.randomUUID().toString()), stack);
		rc.shape("012", "345", "678");
		for (int i = 0; i < 9; i++) {
			if (stacks[i] != null && stacks[i].getType() != Material.AIR) {
				rc.setIngredient(Character.forDigit(i, 10), stacks[i].getType());
			}
		}
	}

	public static void setShieldMeta(ItemStack is, String basecolor, String patternColor, String patternType) {
		if (is.getType() == Material.SHIELD) {
			ItemMeta meta = is.getItemMeta();
			BlockStateMeta bmeta = (BlockStateMeta) meta;
			Banner banner = (Banner) bmeta.getBlockState();
			banner.setBaseColor(DyeColor.valueOf(basecolor));
			banner.addPattern(new Pattern(DyeColor.valueOf(patternColor), PatternType.valueOf(patternType)));
			banner.update();
			bmeta.setBlockState(banner);
			is.setItemMeta(bmeta);
		}
	}

	public static void setShieldMeta(ItemStack is, String basecolor) {
		if (is.getType() == Material.SHIELD) {
			ItemMeta meta = is.getItemMeta();
			BlockStateMeta bmeta = (BlockStateMeta) meta;
			Banner banner = (Banner) bmeta.getBlockState();
			banner.setBaseColor(DyeColor.valueOf(basecolor));
			banner.update();
			bmeta.setBlockState(banner);
			is.setItemMeta(bmeta);
		}
	}

	public static void randomTP(World world, Player player, int radius) {
		Random rd = new Random();
		int x = rd.nextInt(radius + 1) - radius / 2;
		int z = rd.nextInt(radius + 1) - radius / 2;
		int y = world.getHighestBlockYAt(x, z);
		player.teleport(new Location(world, x, y + 1, z));
	}

	private static Map<Integer, String> in = new HashMap<Integer, String>();
	static {
		in.put(-360, "↑");
		in.put(-315, "↖");
		in.put(-270, "←");
		in.put(-225, "↙");
		in.put(-180, "↓");
		in.put(-135, "↘");
		in.put(-90, "→");
		in.put(-45, "↗");
		in.put(0, "↑");
		in.put(45, "↖");
		in.put(90, "←");
		in.put(135, "↙");
		in.put(180, "↓");
		in.put(225, "↘");
		in.put(270, "→");
		in.put(315, "↗");
		in.put(360, "↑");
	}

	public String nearest(Location targetLocation, Location location) {
		targetLocation.setDirection(targetLocation.toVector().subtract(location.toVector()));
		int value = (int) (location.getYaw() - targetLocation.getYaw());
		int min = Integer.MAX_VALUE;
		int closest = value;
		for (int v : in.keySet()) {
			int diff = Math.abs(v - value);
			if (diff < min) {
				min = diff;
				closest = v;
			}
		}
		return in.get(closest) + " " + (int) location.distance(targetLocation) + "m";
	}
}