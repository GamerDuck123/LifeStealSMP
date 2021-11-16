package com.gamerduck.tests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gamerduck.LifeStealMain;
import com.gamerduck.objects.LifeStealPlayer;

public class Test implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		LifeStealMain.a().getLifeStealServer().addOnlinePlayer(e.getPlayer());
		LifeStealPlayer player = LifeStealMain.a().getLifeStealServer().getPlayer(e.getPlayer().getUniqueId());
		player.sendMessage("Things are working... setting hearts to 5");
		player.setHearts(5.0);
	}
}
