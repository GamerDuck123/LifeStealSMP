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
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class WorldGuardHook implements RegionHook {
	private StateFlag LIFESTEAL_FLAG;
	@Override
	public WorldGuardHook enable(LifeStealMain main) {
	    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
	    try {	
	        StateFlag flag = new StateFlag("lifesteal-lose-heart", true);
	        registry.register(flag);
	        LIFESTEAL_FLAG = flag; 
	    } catch (FlagConflictException e) {
	        Flag<?> existing = registry.get("lifesteal-lose-heart");
	        if (existing instanceof StateFlag) LIFESTEAL_FLAG = (StateFlag) existing;
	        else e.printStackTrace();
	    }
		return this;
	}
	
	@Override
	public boolean shouldLoseHeartInRegion(Player player) {
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
		Location loc = BukkitAdapter.adapt(player.getLocation());
		RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
		ApplicableRegionSet set = query.getApplicableRegions(loc);
		return set.testState(localPlayer, LIFESTEAL_FLAG);
	}

}
