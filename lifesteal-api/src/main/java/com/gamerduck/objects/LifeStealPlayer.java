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

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class LifeStealPlayer {
	@Getter final Player handle;
	@Getter final UUID UUID;
	@Getter final String UUIDstring;
	@Getter Player lastDamager;
	
	public LifeStealPlayer(Player handle) {
		this.handle = handle;
		this.UUID = handle.getUniqueId();
		this.UUIDstring = handle.getUniqueId().toString();
		this.lastDamager = null;
		handle.setHealthScaled(true);
		LifeStealServer.a().getDatabase().retrieveHearts(UUIDstring, (retrived) -> {
			if (retrived == -1d) {
				String convertFrom = LifeStealServer.a().getConfig().getString("Defaults.ConvertFrom");
				if (convertFrom == null) handle.setHealthScale(LifeStealServer.a().getConfig().getDouble("Defaults.StartHeartAmount"));
				else convertFrom(convertFrom);
			} else {
				handle.setHealthScale(retrived);
			}
		});
		if (LifeStealServer.a().getConfig().getBoolean("Defaults.ShouldPlayerRecieveRecipeOnJoin")) handle.discoverRecipe(LifeStealServer.a().getCanasterKey());
		updateTABColor();
	}
	
	public void convertFrom(String s) {
		if (s.equalsIgnoreCase("VoodooLifeSteal")) {
			AttributeInstance maxHP = handle.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			String sep = File.separator;
			File file = new File("plugins" + sep + "Lifesteal-Smp-Plugin", "config.yml" );
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			handle.setHealthScale(maxHP.getBaseValue() + ((config.getBoolean("scaleHealth")) ? config.getDouble("healthScale") : 0));
		}
	}
	
	public void onQuit() {
		LifeStealServer.a().getDatabase().storeHearts(UUIDstring, handle.getHealthScale());
	}
	
	public Double getHearts() {return handle.getHealthScale();}
	public boolean resetHearts() {
		handle.setHealthScale(LifeStealServer.a().getConfig().getDouble("Defaults.StartHeartAmount"));
		return handle.getHealth() == LifeStealServer.a().getConfig().getDouble("Defaults.StartHeartAmount");
	}
	public boolean resetHeartsAfterZeroed() {
		handle.setHealthScale(LifeStealServer.a().getConfig().getDouble("Defaults.ZeroedRespawnAmount"));
		return handle.getHealth() == LifeStealServer.a().getConfig().getDouble("Defaults.ZeroedRespawnAmount");
	}
	public boolean setHearts(Double amount) {
		handle.setHealthScale(amount);
		return handle.getHealth() == amount;
	}
	public boolean addHearts(Double amount) {
		handle.setHealthScale(handle.getHealthScale() + amount);
		return handle.getHealthScale() == (handle.getHealthScale() + amount);
	}
	public boolean subHearts(Double amount) {
		handle.setHealthScale(handle.getHealthScale() - amount);
		return handle.getHealthScale() == (handle.getHealthScale() - amount);
	}
	
	public boolean sendMessage(String msg) {handle.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)); return true;}
	public boolean sendMessage(ArrayList<String> msgs) {
		msgs.forEach(s -> handle.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
		return true;
	}
	public boolean sendMessage(List<String> msgs) {
		msgs.forEach(s -> handle.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
		return true;
	}
	public boolean sendMessage(TextComponent text) {handle.spigot().sendMessage(text); return true;}
	
	public boolean hasPermission(Permission perm) {return handle.hasPermission(perm);}
	public boolean hasPermission(String perm) {return handle.hasPermission(perm);}
	
	public boolean updateTABColor() {
		if (!LifeStealServer.a().getConfig().getBoolean("TAB.Enabled")) return false;
		for (String s : LifeStealServer.a().getConfig().getStringList("TAB.Colors")) {
			String[] split = s.split(":");
			double amount = isNumeric(split[0]) ? Double.parseDouble(split[0]) : 0.0;
			ChatColor color = ChatColor.getByChar(split[1].charAt(1));
			if (this.getHearts() <= amount) {
				handle.setPlayerListName(color + handle.getName());
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
	
	BukkitTask lastDamagerTask;
	public void setLastDamager(Player player) {
		lastDamager = player;
		if (lastDamagerTask != null) {lastDamagerTask.cancel();}
		lastDamagerTask = Bukkit.getScheduler().runTaskLater(LifeStealServer.a().getPlugin(), new Runnable() {
            @Override
            public void run() {
            	clearLastDamager();
            }
    	}, LifeStealServer.a().getConfig().getInt("Defaults.HowLongShouldPlayerBeLastDamager") * 20);
	}
	
	public void clearLastDamager() {
		lastDamager = null;
	}
}
