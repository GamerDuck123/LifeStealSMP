package com.gamerduck.hooks.region;

import org.bukkit.entity.Player;

import com.gamerduck.LifeStealMain;

public class GriefPreventionHook implements RegionHook {

	@Override
	public RegionHook enable(LifeStealMain main) {
		// TODO
		return this;
	}

	@Override
	public boolean shouldLoseHeartInRegion(Player player) {
		// TODO
		return true;
	}

}
