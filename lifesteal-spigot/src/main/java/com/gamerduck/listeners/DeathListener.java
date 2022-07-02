package com.gamerduck.listeners;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gamerduck.GlobalMethods;
import com.gamerduck.enums.LifeReason;
import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

public class DeathListener implements Listener, GlobalMethods {

	@EventHandler
	public void onDeath(EntityDeathEvent e)  {
		if (e.getEntity() instanceof Player p) {
			if (getConfig().getStringList("Defaults.DisabledWorlds").contains(e.getEntity().getWorld().getName())) return;
			LifeStealPlayer pl = LifeStealServer.a().getPlayer(p);
			if (p.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
				if (p.getLastDamageCause() != null
						&& p.getKiller() instanceof Player) {
					Player killer = p.getKiller();
					try {
						loseLife(p, getConfig().getDouble("Defaults.HeartsLostOnDeath"), LifeReason.KILL);
						gainLife(killer, getConfig().getDouble("Defaults.HeartsGainedOnKill"), LifeReason.KILL);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else if (pl.getLastDamager() != null) {
				Player killer = pl.getLastDamager();
				try {
					loseLife(p, getConfig().getDouble("Defaults.HeartsLostOnDeath"), LifeReason.KILL);
					gainLife(killer, getConfig().getDouble("Defaults.HeartsGainedOnKill"), LifeReason.KILL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (p.getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION) { 
				Entity ent = ((EntityDamageByEntityEvent) e.getEntity().getLastDamageCause()).getDamager();
				switch (ent.getType()) {
				case ENDER_CRYSTAL: {
					if (ent.hasMetadata("PLAYER_PLACED")) {
						Player placer = (Player) ent.getMetadata("PLAYER_PLACED").get(0).value();
						if (placer == p.getPlayer()) return;
						try {
							loseLife(p, getConfig().getDouble("Defaults.HeartsLostOnDeath"), LifeReason.KILL);
							gainLife(placer, getConfig().getDouble("Defaults.HeartsGainedOnKill"), LifeReason.KILL);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
				}
				default: break;
				}
			} else {
				if (getConfig().getBoolean("Defaults.LoseHeartsOnNonPlayerDeath")) {
					try {
						loseLife(p, getConfig().getDouble("Defaults.HeartsGainedOnKill"), LifeReason.DEATH);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	//	
	//	public void loseLife(Player player, double amount, LifeReason reason) {
	//		LifeStealPlayer p = LifeStealServer.a().getPlayer(player);
	//		LifeLostEvent event = new LifeLostEvent(p, reason, amount);
	//		Bukkit.getServer().getPluginManager().callEvent(event);
	//		if (p.getHearts() - amount <= zeroedAmount) {
	//			p.resetHeartsAfterZeroed();
	//			zeroedCommands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
	//		} else {
	//			p.subHearts(amount);
	//			p.sendMessage(tl("HeartsLost", amount, p.getHearts()));
	//			p.updateTABColor();
	//			afterDeathCommands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
	//		}
	//	}
	//	
	//	public void gainLife(Player player, double amount, LifeReason reason) {
	//		LifeStealPlayer p = LifeStealServer.a().getPlayer(player);
	//		LifeGainEvent event2 = new LifeGainEvent(p, reason, amount);
	//		Bukkit.getServer().getPluginManager().callEvent(event2);
	//		if ((p.getHearts() + amount) > maxHeartAmount) {
	//			p.sendMessage(tl("MaxHearts", amount, p.getHearts()));
	//			afterKillCommands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
	//		} else {
	//			p.addHearts(amount);
	//			p.sendMessage(tl("HeartsGained", amount, p.getHearts()));
	//			p.updateTABColor();
	//			afterKillCommands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
	//		}
	//	}
	//	
}
