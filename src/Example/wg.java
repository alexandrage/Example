package Example;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class wg {
	protected static WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");

	public static void get(Player player, String id, World world) throws CommandException {
		LocalPlayer localPlayer = wg.wrapPlayer(player);
		RegionManager manager = wg.getRegionContainer().get(world);
		checkRegionDoesNotExist(manager, id, false);
		ApplicableRegionSet regions = manager.getApplicableRegions(checkRegionFromSelection(player, id));
		if (regions.size() > 0) {
			if (!regions.isOwnerOfAll(localPlayer)) {
				throw new CommandException("This region overlaps with someone else's region.");
			}
		}
	}

	protected static Selection checkSelection(Player player) throws CommandException {
		WorldEditPlugin worldEdit = WorldGuardPlugin.inst().getWorldEdit();
		Selection selection = worldEdit.getSelection(player);
		if (selection == null) {
			throw new CommandException(
					"Please select an area first. Use WorldEdit to make a selection! (wiki: http://wiki.sk89q.com/wiki/WorldEdit).");
		}
		return selection;
	}

	protected static ProtectedRegion checkRegionFromSelection(Player player, String id) throws CommandException {
		Selection selection = checkSelection(player);
		if ((selection instanceof Polygonal2DSelection)) {
			Polygonal2DSelection polySel = (Polygonal2DSelection) selection;
			int minY = polySel.getNativeMinimumPoint().getBlockY();
			int maxY = polySel.getNativeMaximumPoint().getBlockY();
			return new ProtectedPolygonalRegion(id, polySel.getNativePoints(), minY, maxY);
		}
		if ((selection instanceof CuboidSelection)) {
			BlockVector min = selection.getNativeMinimumPoint().toBlockVector();
			BlockVector max = selection.getNativeMaximumPoint().toBlockVector();
			return new ProtectedCuboidRegion(id, min, max);
		}
		throw new CommandException("Sorry, you can only use cuboids and polygons for WorldGuard regions.");
	}

	protected static String checkRegionId(String id, boolean allowGlobal) throws CommandException {
		if (!ProtectedRegion.isValidId(id)) {
			throw new CommandException("The region name of '" + id + "' contains characters that are not allowed.");
		}
		if ((!allowGlobal) && (id.equalsIgnoreCase("__global__"))) {
			throw new CommandException("Sorry, you can't use __global__ here.");
		}
		return id;
	}

	protected static void checkRegionDoesNotExist(RegionManager manager, String id, boolean mayRedefine)
			throws CommandException {
		if (manager.hasRegion(id)) {
			throw new CommandException("A region with that name already exists. Please choose another name."
					+ (mayRedefine ? " To change the shape, use /region redefine " + id + "." : ""));
		}
	}
}