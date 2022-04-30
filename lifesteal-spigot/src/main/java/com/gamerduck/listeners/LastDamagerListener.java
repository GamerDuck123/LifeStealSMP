package com.gamerduck.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

public class LastDamagerListener implements Listener {
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			LifeStealPlayer p = LifeStealServer.a().getPlayer((Player)e.getEntity());;
			if (p.getLastDamager() == damager) {return;}
			p.setLastDamager(damager);
		}
	}
}
