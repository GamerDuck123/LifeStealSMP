package com.gamerduck.hooks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolderAPIHook extends PlaceholderExpansion {

	    private final LifeStealServer server;
	    
	    public PlaceHolderAPIHook(LifeStealServer server) {
	        this.server = server;
	    }
	    
	    @Override
	    public String getAuthor() {
	        return "GamerDuck123";
	    }
	    
	    @Override
	    public String getIdentifier() {
	        return "lifesteal";
	    }

	    @Override
	    public String getVersion() {
	        return "1.0";
	    }
	    
	    @Override
	    public boolean persist() {
	        return true; 
	    }
	    
	    @Override
	    public String onRequest(OfflinePlayer player, String params) {
	    	if (player == null || !Bukkit.getPlayer(player.getUniqueId()).isOnline()) return null;
	    	LifeStealPlayer p = server.getPlayer(Bukkit.getPlayer(player.getUniqueId()));
	    	if (params.equalsIgnoreCase("hearts")) {
	    		return String.valueOf(p.getHearts());
	    	}
	        return null;
	    }
}
