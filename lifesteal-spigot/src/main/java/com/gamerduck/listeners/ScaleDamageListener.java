package com.gamerduck.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class ScaleDamageListener implements Listener {
	@EventHandler
	public void scaleDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			double amount = (((Player) e.getEntity()).getHealthScale()) / 20;
			e.setDamage(e.getDamage() / amount);
		}
	}
	
	@EventHandler
	public void scaleHeal(EntityRegainHealthEvent e) {
		if (e.getEntity() instanceof Player) {
			double amount = (((Player) e.getEntity()).getHealthScale()) / 20;
			e.setAmount(e.getAmount() / amount);
		}
	}
}
