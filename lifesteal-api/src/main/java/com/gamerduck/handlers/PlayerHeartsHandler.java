package com.gamerduck.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gamerduck.LifeStealAPI;
import com.gamerduck.configs.values;
import com.gamerduck.enums.LifeReason;
import com.gamerduck.events.LifeGainEvent;
import com.gamerduck.events.LifeLostEvent;
import com.gamerduck.objects.LifeStealPlayer;

public class PlayerHeartsHandler implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (p.getLastDamageCause() != null && p.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK
					&& p.getKiller() instanceof Player) {
				LifeLostEvent event = new LifeLostEvent(p, LifeReason.KILL, values.DEFAULT_HEARTS_LOST_ON_DEATH);
				Bukkit.getServer().getPluginManager().callEvent(event);
				Player killer = p.getKiller();
				LifeGainEvent event2 = new LifeGainEvent(killer, LifeReason.KILL, values.DEFAULT_HEARTS_GAINED_ON_KILL);
				Bukkit.getServer().getPluginManager().callEvent(event2);
			} else {
				if (values.DEFAULT_LOSE_HEARTS_ON_NON_PLAYER_DEATH) {
					LifeLostEvent event = new LifeLostEvent(p, LifeReason.DEATH, values.DEFAULT_HEARTS_LOST_ON_DEATH);
					Bukkit.getServer().getPluginManager().callEvent(event);
				} else {
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onLifeLost(LifeLostEvent e) {
		LifeStealPlayer p = LifeStealAPI.a().getServer().getPlayer(e.getPlayer());
		if (p.getHearts() - e.getAmount() <= values.DEFAULT_HEARTS_ZEROED_AMOUNT) {
			p.resetHearts();
			values.DEFAULT_ZEROED_COMMANDS.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", e.getPlayer().getName())));
		} else {
			p.subHearts(e.getAmount());
			p.sendMessage(values.MESSAGES_HEARTS_LOST.
					replaceAll("%amount%", "" + e.getAmount())
					.replaceAll("%total%", "" + p.getHearts()));
			p.updateTABColor();
		}
	}
	
	@EventHandler
	public void onLifeLost(LifeGainEvent e) {
		LifeStealPlayer p = LifeStealAPI.a().getServer().getPlayer(e.getPlayer());
		p.addHearts(e.getAmount());
		p.sendMessage(values.MESSAGES_HEARTS_GAINED.
				replaceAll("%amount%", "" + e.getAmount())
				.replaceAll("%total%", "" + p.getHearts()));
		p.updateTABColor();
	}
	
}
