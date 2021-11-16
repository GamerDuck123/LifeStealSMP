package com.gamerduck.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gamerduck.LifeStealAPI;

public class ServerHandlers implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		LifeStealAPI.a().getServer().addOnlinePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		LifeStealAPI.a().getServer().getPlayer(e.getPlayer().getName()).onQuit();
		LifeStealAPI.a().getServer().removeOnlinePlayer(LifeStealAPI.a().getServer().getPlayer(e.getPlayer().getName()));
	}
}
