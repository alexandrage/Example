package Example.wge;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class wg {
	protected static WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	protected static WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	public static WorldGuardPlatform wp = WorldGuard.getInstance().getPlatform();

	public static void claim(Player player, String id, World world) throws CommandException {
		LocalPlayer localPlayer = wg.wrapPlayer(player);
		RegionManager manager =  checkRegionManager(wg, BukkitAdapter.adapt(world));
		checkRegionDoesNotExist(manager, id, false);
		checkRegionId(id, false);
		ApplicableRegionSet regions = manager.getApplicableRegions(checkRegionFromSelection(player, id));
		if (regions.size() > 0) {
			if (!regions.isOwnerOfAll(localPlayer)) {
				throw new CommandException("Эта область перекрывается с чужой областью.");
			}
		}
		ProtectedRegion region = checkRegionFromSelection(player, id);
		if ((region instanceof ProtectedPolygonalRegion)) {
			throw new CommandException("Полигоны в настоящее время не поддерживаются для /rg claim.");
		}
		DefaultDomain owners = region.getOwners();
		owners.addPlayer(localPlayer);
		region.setOwners(owners);
		manager.addRegion(region);
	}

    protected static Region checkSelection(Player player) throws CommandException {
        final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        try {
            return WorldEdit.getInstance().getSessionManager().get(localPlayer).getRegionSelector(localPlayer.getWorld()).getRegion();
        }
        catch (IncompleteRegionException e) {
            throw new CommandException("Сначала выберите область. Используйте WorldEdit, чтобы сделать выбор! (wiki: http://wiki.sk89q.com/wiki/WorldEdit).");
        }
    }
	
    protected static ProtectedRegion checkRegionFromSelection(Player player, String id) throws CommandException {
        final Region selection = checkSelection(player);
        if (selection instanceof Polygonal2DRegion) {
            final Polygonal2DRegion polySel = (Polygonal2DRegion)selection;
            final int minY = polySel.getMinimumPoint().getBlockY();
            final int maxY = polySel.getMaximumPoint().getBlockY();
            return (ProtectedRegion)new ProtectedPolygonalRegion(id, polySel.getPoints(), minY, maxY);
        }
        if (selection instanceof CuboidRegion) {
            final BlockVector3 min = selection.getMinimumPoint();
            final BlockVector3 max = selection.getMaximumPoint();
            return (ProtectedRegion)new ProtectedCuboidRegion(id, min, max);
        }
        throw new CommandException("К сожалению, вы можете использовать только кубоиды и полигоны для регионов WorldGuard.");
    }

	private static String checkRegionId(String id, boolean allowGlobal) throws CommandException {
		if (!ProtectedRegion.isValidId(id)) {
			throw new CommandException("Название области '" + id + "' содержит символы, которые не разрешены.");
		}
		if ((!allowGlobal) && (id.equalsIgnoreCase("__global__"))) {
			throw new CommandException("Извините, здесь вы не можете использовать __global__.");
		}
		return id;
	}

	private static void checkRegionDoesNotExist(RegionManager manager, String id, boolean mayRedefine)
			throws CommandException {
		if (manager.hasRegion(id)) {
			throw new CommandException("Область с таким именем уже существует. Выберите другое имя."
					+ (mayRedefine ? " Чтобы изменить форму, используйте /region redefine " + id + "." : ""));
		}
	}

    protected static RegionManager checkRegionManager(WorldGuardPlugin plugin, com.sk89q.worldedit.world.World world) throws CommandException {
        if (!WorldGuard.getInstance().getPlatform().getGlobalStateManager().get(world).useRegions) {
            throw new CommandException("Region support is disabled in the target world. It can be enabled per-world in WorldGuard's configuration files. However, you may need to restart your server afterwards.");
        }
        final RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        if (manager == null) {
            throw new CommandException("Region data failed to load for this world. Please ask a server administrator to read the logs to identify the reason.");
        }
        return manager;
    }
    
	public static boolean canBuild(Player p) {
		return wp.getRegionContainer().createQuery().testBuild(BukkitAdapter.adapt(p.getLocation()), wg.wrapPlayer(p));
	}
}