package com.gamerduck.hooks.region;

import org.bukkit.entity.Player;

import com.gamerduck.LifeStealMain;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class WorldGuardHook implements RegionHook {
	private StateFlag LIFESTEAL_FLAG;
	@Override
	public WorldGuardHook enable(LifeStealMain main) {

	    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
	    try {
	        // create a flag with the name "my-custom-flag", defaulting to true
	        StateFlag flag = new StateFlag("lifesteal-lose-heart", true);
	        registry.register(flag);
	        LIFESTEAL_FLAG = flag; // only set our field if there was no error
	    } catch (FlagConflictException e) {
	        // some other plugin registered a flag by the same name already.
	        // you can use the existing flag, but this may cause conflicts - be sure to check type
	        Flag<?> existing = registry.get("lifesteal-lose-heart");
	        if (existing instanceof StateFlag) {
	        	LIFESTEAL_FLAG = (StateFlag) existing;
	        } else {
	            // types don't match - this is bad news! some other plugin conflicts with you
	            // hopefully this never actually happens
	        }
	    }
		return this;
	}
	
	@Override
	public boolean shouldLoseHeartInRegion(Player player) {
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
		Location loc = BukkitAdapter.adapt(player.getLocation());
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		ApplicableRegionSet set = query.getApplicableRegions(loc);
		return set.testState(localPlayer, LIFESTEAL_FLAG);
	}

}
