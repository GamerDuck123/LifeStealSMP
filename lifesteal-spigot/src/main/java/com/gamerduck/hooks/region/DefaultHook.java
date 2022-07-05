package com.gamerduck.hooks.region;

import org.bukkit.entity.Player;

import com.gamerduck.LifeStealMain;

public class DefaultHook implements RegionHook {
	@Override
	public DefaultHook enable(LifeStealMain main) {
		return this;
	}
	
	@Override
	public boolean shouldLoseHeartInRegion(Player player) {
		return true;
	}

}
