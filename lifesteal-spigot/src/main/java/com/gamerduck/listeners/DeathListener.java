package com.gamerduck.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gamerduck.configs.values;
import com.gamerduck.enums.LifeReason;
import com.gamerduck.events.LifeGainEvent;
import com.gamerduck.events.LifeLostEvent;
import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

public class DeathListener implements Listener {
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			LifeStealPlayer pl = LifeStealServer.a().getPlayer(p);
			if (p.getLastDamageCause() != null && p.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK
					&& p.getKiller() instanceof Player) {
				Player killer = p.getKiller();
				loseLife(p, values.DEFAULT_HEARTS_LOST_ON_DEATH, LifeReason.KILL);
				gainLife(killer, values.DEFAULT_HEARTS_GAINED_ON_KILL, LifeReason.KILL);
			} else if (pl.getLastDamager() != null) {
				Player killer = pl.getLastDamager();
				loseLife(p, values.DEFAULT_HEARTS_LOST_ON_DEATH, LifeReason.KILL);
				gainLife(killer, values.DEFAULT_HEARTS_GAINED_ON_KILL, LifeReason.KILL);
			} else {
				if (values.DEFAULT_LOSE_HEARTS_ON_NON_PLAYER_DEATH) {
					loseLife(p, values.DEFAULT_HEARTS_LOST_ON_DEATH, LifeReason.DEATH);
				} else {
					return;
				}
			}
		}
	}
	
	public void loseLife(Player player, double amount, LifeReason reason) {
		LifeStealPlayer p = LifeStealServer.a().getPlayer(player);
		LifeLostEvent event = new LifeLostEvent(p, reason, amount);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (p.getHearts() - amount <= values.DEFAULT_HEARTS_ZEROED_AMOUNT) {
			p.resetHearts();
			values.DEFAULT_ZEROED_COMMANDS.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", player.getName())));
		} else {
			p.subHearts(amount);
			p.sendMessage(values.MESSAGES_HEARTS_LOST.replaceAll("%amount%", "" + amount).replaceAll("%total%", "" + p.getHearts()));
			p.updateTABColor();
			values.DEFAULT_AFTER_DEATH_COMMANDS.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getName())));
		}
	}
	
	public void gainLife(Player player, double amount, LifeReason reason) {
		LifeStealPlayer p = LifeStealServer.a().getPlayer(player);
		LifeGainEvent event2 = new LifeGainEvent(p, reason, values.DEFAULT_HEARTS_GAINED_ON_KILL);
		Bukkit.getServer().getPluginManager().callEvent(event2);
		p.addHearts(amount);
		p.sendMessage(values.MESSAGES_HEARTS_GAINED.replaceAll("%amount%", "" + amount).replaceAll("%total%", "" + p.getHearts()));
		p.updateTABColor();
		values.DEFAULT_AFTER_KILL_COMMANDS.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getName())));

	}
	
}
