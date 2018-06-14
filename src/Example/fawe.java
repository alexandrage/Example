package Example;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class fawe {
	public static void regen(org.bukkit.World bworld, String id, boolean isRemove) {
		WorldGuardPlugin wg = WorldGuardPlugin.inst();
		RegionManager manager = wg.getRegionContainer().get(bworld);
		ProtectedRegion protectedregion = manager.getRegion(id);
		BlockVector min = protectedregion.getMinimumPoint();
		BlockVector max = protectedregion.getMaximumPoint();
		World world = FaweAPI.getWorld(bworld.getName());
		EditSession editSession = new EditSessionBuilder(world).fastmode(true).build();
		Region region = new CuboidRegion(world, min, max);
		editSession.regenerate(region);
		if (isRemove) {
			manager.removeRegion(id);
		}
		Bukkit.getLogger().info("Region " + id + " regenerated.");
	}
	
	public static void paste(File file, org.bukkit.World bworld, org.bukkit.util.Vector vector) {
		try {
			Vector position = new Vector(vector.getX(),vector.getY(),vector.getZ());
			World world = FaweAPI.getWorld(bworld.getName());
			ClipboardFormat.SCHEMATIC.load(file).paste(world, position, true, false, (Transform) null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}