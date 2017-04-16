package Example;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.NumberConversions;

@SerializableAs(value="Loc")
public class Loc implements ConfigurationSerializable {

	World world;
	double x;
	double y;
	double z;
	
	public Loc(Location loc) {
		world = loc.getWorld();
		x = loc.getBlockX()+0.5;
		y = loc.getBlockY();
		z = loc.getBlockZ()+0.5;
	}

    public static Location deserialize(Map args) {
        World world = Bukkit.getWorld((String)args.get("world"));
        if(world == null)
            throw new IllegalArgumentException("unknown world");
        else
            return new Location(world, NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")));
    }

	@Override
	public Map serialize() {
        Map data = new HashMap();
        data.put("world", world.getName());
        data.put("x", Double.valueOf(x));
        data.put("y", Double.valueOf(y));
        data.put("z", Double.valueOf(z));
        return data;
	}
}
