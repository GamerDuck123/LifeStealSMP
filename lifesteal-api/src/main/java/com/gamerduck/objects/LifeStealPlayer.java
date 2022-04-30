package com.gamerduck.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitTask;

import com.gamerduck.configs.values;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class LifeStealPlayer {
	Player p;
	UUID uuid;
	String uuidstring;
	Player lastDamager;
	
	public LifeStealPlayer(Player p) {
		this.p = p;
		this.uuid = p.getUniqueId();
		this.uuidstring = p.getUniqueId().toString();
		this.lastDamager = null;
		p.setHealthScaled(true);
		if (LifeStealServer.a().getDatabase().retrieveHearts(uuidstring) != -1d) {
			p.setHealthScale(LifeStealServer.a().getDatabase().retrieveHearts(uuidstring));
		} else {
			if (values.DEFAULT_CONVERT_FROM == null && values.DEFAULT_CONVERT_FROM.equalsIgnoreCase("")) {
				p.setHealthScale(values.DEFAULT_HEART_AMOUNT);
			} else {
				convertFrom(values.DEFAULT_CONVERT_FROM);
			}
		}
		updateTABColor();
	}
	
	public void convertFrom(String s) {
		if (s.equalsIgnoreCase("VoodooLifeSteal")) {
			AttributeInstance maxHP = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			String sep = File.separator;
			File file = new File("plugins" + sep + "Lifesteal-Smp-Plugin", "config.yml" );
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			p.setHealthScale(maxHP.getBaseValue() + ((config.getBoolean("scaleHealth")) ? config.getDouble("healthScale") : 0));
		}
	}
	
	public void onQuit() {
		LifeStealServer.a().getDatabase().storeHearts(uuidstring, p.getHealthScale());
	}
	
	public Player getPlayer() {return p;}
	public String getName() {return p.getName();}
	public UUID getUUID() {return uuid;}
	public String getUUIDAsString() {return uuidstring;}
	
	public Double getHearts() {return p.getHealthScale();}
	public boolean resetHearts() {
		p.setHealthScale(values.DEFAULT_HEART_AMOUNT);
		return p.getHealth() == values.DEFAULT_HEART_AMOUNT;
	}
	public boolean setHearts(Double amount) {
		p.setHealthScale(amount);
		return p.getHealth() == amount;
	}
	public boolean addHearts(Double amount) {
		p.setHealthScale(p.getHealthScale() + amount);
		return p.getHealthScale() == (p.getHealthScale() + amount);
	}
	public boolean subHearts(Double amount) {
		p.setHealthScale(p.getHealthScale() - amount);
		return p.getHealthScale() == (p.getHealthScale() - amount);
	}
	
	public boolean sendMessage(String msg) {p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)); return true;}
	public boolean sendMessage(ArrayList<String> msgs) {
		msgs.forEach(s -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
		return true;
	}
	public boolean sendMessage(List<String> msgs) {
		msgs.forEach(s -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
		return true;
	}
	public boolean sendMessage(TextComponent text) {p.spigot().sendMessage(text); return true;}
	
	public boolean hasPermission(Permission perm) {return p.hasPermission(perm);}
	public boolean hasPermission(String perm) {return p.hasPermission(perm);}
	
	public boolean updateTABColor() {
		if (values.TAB_ENABLED == false) return false;
		for (String s : values.TAB_COLORS) {
			String[] split = s.split(":");
			double amount = isNumeric(split[0]) ? Double.parseDouble(split[0]) : 0.0;
			ChatColor color = ChatColor.getByChar(split[1].charAt(1));
			if (this.getHearts() <= amount) {
				p.setPlayerListName(color + p.getName());
			}
		}
		return true;
	}
	
	private boolean isNumeric(String strNum) {
	    if (strNum == null) return false;
	    try { Double.parseDouble(strNum); }
	    catch (NumberFormatException nfe) { return false; }
	    return true;
	}
	
	public Player getLastDamager() {
		return lastDamager;
	}
	
	BukkitTask lastDamagerTask;
	public void setLastDamager(Player player) {
		lastDamager = player;
		if (lastDamagerTask != null) {lastDamagerTask.cancel();}
		lastDamagerTask = Bukkit.getScheduler().runTaskLater(LifeStealServer.a().getPlugin(), new Runnable() {
            @Override
            public void run() {
            	clearLastDamager();
            }
    	}, values.HOW_LONG_SHOULD_PLAYER_BE_LAST_DAMAGER * 20);
	}
	
	public void clearLastDamager() {
		lastDamager = null;
	}
}
