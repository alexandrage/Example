package Example.wge;

import java.io.File;
import java.io.IOException;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.world.World;

public class fawe {
	public static void paste(File file, org.bukkit.World bworld, org.bukkit.util.Vector vector) {
		try {
			Vector position = new Vector(vector.getX(), vector.getY(), vector.getZ());
			World world = FaweAPI.getWorld(bworld.getName());
			ClipboardFormat.findByAlias("schematic").load(file).paste(world, position, true, false, (Transform) null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}