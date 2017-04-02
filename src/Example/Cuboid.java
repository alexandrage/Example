package Example;
public class Cuboid {
    private long xMin;
    private long xMax;
    private long yMin;
    private long yMax;
    private long zMin;
    private long zMax;
    public Cuboid(long x1, long y1, long z1, long x2, long y2, long z2) {
        if (x1 < x2) {
            xMin = x1;
            xMax = x2;
        } else {
            xMin = x2;
            xMax = x1;
        }
        if (y1 < y2) {
            yMin = y1;
            yMax = y2;
        } else {
            yMin = y2;
            yMax = y1;
        }
        if (z1 < z2) {
            zMin = z1;
            zMax = z2;
        } else {
            zMin = z2;
            zMax = z1;
        }
    }
    public boolean intersects(Cuboid cuboid) {
        return cuboid.xMin <= xMax && cuboid.xMax >= xMin
               && cuboid.yMin <= yMax && cuboid.yMax >= yMin
               && cuboid.zMin <= zMax && cuboid.zMax >= zMin;
    }
   
    public boolean contains(long x, long y, long z) {
        return x >= xMin && x <= xMax
               && y >= yMin && y <= yMax
               && z >= zMin && z <= zMax;
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
}