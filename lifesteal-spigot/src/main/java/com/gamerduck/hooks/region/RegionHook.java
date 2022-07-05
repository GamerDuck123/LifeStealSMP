package com.gamerduck.hooks.region;

import org.bukkit.entity.Player;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;

public interface RegionHook extends GlobalMethods {
	public RegionHook enable(LifeStealMain main);
	public boolean shouldLoseHeartInRegion(Player player);
}
