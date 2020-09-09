package Example.wge;

import java.io.File;
import java.io.IOException;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.world.World;

public class fawe {
	public static void paste(File file, org.bukkit.World bworld, org.bukkit.util.Vector vector) {
		try {
			BlockVector3 position = BlockVector3.at(vector.getX(), vector.getY(), vector.getZ());
			World world = FaweAPI.getWorld(bworld.getName());
			BuiltInClipboardFormat.MCEDIT_SCHEMATIC.load(file).paste(world, position, true, false, (Transform) null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}