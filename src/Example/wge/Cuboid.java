package Example.wge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cuboid implements Iterable<Block> {
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int zMin;
	private int zMax;
	private String world;

	public Cuboid(Location loc1, Location loc2) {
		normalize(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(),
				loc2.getBlockZ());
	}

	public Cuboid(int x1, int y1, int z1, int x2, int y2, int z2) {
		normalize(x1, y1, z1, x2, y2, z2);
	}

	private void normalize(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.xMin = Math.min(x1, x2);
		this.xMax = Math.max(x1, x2);
		this.yMin = Math.min(y1, y2);
		this.yMax = Math.max(y1, y2);
		this.zMin = Math.min(z1, z2);
		this.zMax = Math.max(z1, z2);
	}

	public boolean intersects(Cuboid cuboid) {
		return cuboid.xMin <= this.xMax && cuboid.xMax >= this.xMin && cuboid.yMin <= this.yMax
				&& cuboid.yMax >= this.yMin && cuboid.zMin <= this.zMax && cuboid.zMax >= this.zMin;
	}

	public boolean contains(Location loc) {
		return contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public boolean contains(long x, long y, long z) {
		return x >= this.xMin && x <= this.xMax && y >= this.yMin && y <= this.yMax && z >= this.zMin && z <= this.zMax;
	}

	public long getVolume() {
		return getWidth() * getHeight() * getDepth();
	}

	public long getWidth() {
		return this.xMax - this.xMin + 1;
	}

	public long getHeight() {
		return this.yMax - this.yMin + 1;
	}

	public long getDepth() {
		return this.zMax - this.zMin + 1;
	}

	public World getWorld() {
		return Bukkit.getWorld(this.world);
	}

	public Cuboid expand(CuboidDirection dir, int amount) {
		switch (dir) {
		case North:
			this.xMin = this.xMin - amount;
			return this;
		case South:
			this.xMax = this.xMax + amount;
			return this;
		case East:
			this.zMin = this.zMin - amount;
			return this;
		case West:
			this.zMax = this.zMax + amount;
			return this;
		case Down:
			this.yMin = this.yMin - amount;
			return this;
		case Up:
			this.yMax = this.yMax + amount;
			return this;
		default:
			throw new IllegalArgumentException("invalid direction " + dir);
		}
	}

	public Cuboid shift(CuboidDirection dir, int amount) {
		return expand(dir, amount).expand(dir.opposite(), -amount);
	}

	public Cuboid outset(int amount) {
		Cuboid c = expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount)
				.expand(CuboidDirection.East, amount).expand(CuboidDirection.West, amount)
				.expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
		return c;
	}

	public Cuboid inset(int amount) {
		return outset(-amount);
	}

	public List<Block> getBlocks() {
		List<Block> blockList = new ArrayList<Block>();
		World world = this.getWorld();
		for (int x = this.xMin; x <= this.xMax; x++) {
			for (int y = this.yMin; y <= this.yMax; y++) {
				for (int z = this.zMin; z <= this.zMax; z++) {
					blockList.add(world.getBlockAt(x, y, z));
				}
			}
		}
		return blockList;
	}

	public Location getCenter() {
		return new Location(this.getWorld(), (double) ((this.xMax - this.xMin) / 2 + this.xMin),
				(double) ((this.yMax - this.yMin) / 2 + this.yMin), (double) ((this.zMax - this.zMin) / 2 + this.zMin));
	}

	@Override
	public Iterator<Block> iterator() {
		return this.getBlocks().listIterator();
	}

	@Override
	public String toString() {
		return "Cuboid: " + this.xMin + "," + this.yMin + "," + this.zMin + "=>" + this.xMax + "," + this.yMax + ","
				+ this.zMax;
	}
}