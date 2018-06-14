package Example;

import org.bukkit.Location;

public class Cuboid {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	private long zMin;
	private long zMax;

	public Cuboid(Location loc1, Location loc2) {
		normalize(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(),
				loc2.getBlockZ());
	}

	public Cuboid(long x1, long y1, long z1, long x2, long y2, long z2) {
		normalize(x1, y1, z1, x2, y2, z2);
	}

	private void normalize(long x1, long y1, long z1, long x2, long y2, long z2) {
		this.xMin = Math.min(x1, x2);
		this.xMax = Math.max(x1, x2);
		this.yMin = Math.min(y1, y2);
		this.yMax = Math.max(y1, y2);
		this.zMin = Math.min(z1, z2);
		this.zMax = Math.max(z1, z2);
	}

	public boolean intersects(Cuboid cuboid) {
		return cuboid.xMin <= xMax && cuboid.xMax >= xMin && cuboid.yMin <= yMax && cuboid.yMax >= yMin
				&& cuboid.zMin <= zMax && cuboid.zMax >= zMin;
	}

	public boolean contains(Location loc) {
		return contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public boolean contains(long x, long y, long z) {
		return x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax;
	}

	public long getVolume() {
		return getWidth() * getHeight() * getDepth();
	}

	public long getWidth() {
		return xMax - xMin + 1;
	}

	public long getHeight() {
		return yMax - yMin + 1;
	}

	public long getDepth() {
		return zMax - zMin + 1;
	}

	public Cuboid expand(CuboidDirection dir, int amount) {
		switch (dir) {
		case North:
			return new Cuboid(xMin - amount, yMin, zMin, xMax, yMax, zMax);
		case South:
			return new Cuboid(xMin, yMin, zMin, xMax + amount, yMax, zMax);
		case East:
			return new Cuboid(xMin, yMin, zMin - amount, xMax, yMax, zMax);
		case West:
			return new Cuboid(xMin, yMin, zMin, xMax, yMax, zMax + amount);
		case Down:
			return new Cuboid(xMin, yMin - amount, zMin, xMax, yMax, zMax);
		case Up:
			return new Cuboid(xMin, yMin, zMin, xMax, yMax + amount, zMax);
		default:
			throw new IllegalArgumentException("invalid direction " + dir);
		}
	}

	public Cuboid shift(CuboidDirection dir, int amount) {
		return expand(dir, amount).expand(dir.opposite(), -amount);
	}

	public Cuboid outset(CuboidDirection dir, int amount) {
		Cuboid c = expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount)
				.expand(CuboidDirection.East, amount).expand(CuboidDirection.West, amount)
				.expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
		return c;
	}

	public Cuboid inset(CuboidDirection dir, int amount) {
		return outset(dir, -amount);
	}

	@Override
	public String toString() {
		return "Cuboid: " + xMin + "," + yMin + "," + zMin + "=>" + xMax + "," + yMax + "," + zMax;
	}
}