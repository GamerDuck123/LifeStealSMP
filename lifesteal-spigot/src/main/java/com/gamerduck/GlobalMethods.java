package com.gamerduck;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gamerduck.enums.LifeReason;
import com.gamerduck.events.LifeGainEvent;
import com.gamerduck.events.LifeLostEvent;
import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

import net.md_5.bungee.api.ChatColor;

public interface GlobalMethods {

    final ResourceBundle bundle = LifeStealMain.a().getMessagesBundle();
    default String tl(String s, Object... objects) {
    	MessageFormat messageFormat = new MessageFormat(bundle.getString(s));
    	return bundle.getString(s).equalsIgnoreCase("") ? null : color(messageFormat.format(objects).replace(' ', ' ')); 
    }
    
    final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    default String color(String textToTranslate) {
    	Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
    	StringBuffer buffer = new StringBuffer();
    	while(matcher.find()) matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
    	return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    } 
    default List<String> color(List<String> s) {
    	List<String> returnList = new ArrayList<String>();
    	s.forEach(st -> {
    		returnList.add(color(st));
    	});
    	return returnList;
    }
	
	public default void loseLife(Player player, double amount, LifeReason reason) throws Exception {
		if (!LifeStealMain.getInstance().getRegionHook().shouldLoseHeartInRegion(player)) return;
		LifeStealPlayer p = LifeStealServer.a().getPlayer(player);
		if (player == null || !player.isOnline() || p == null) {
			loseLifeOffline(player.getUniqueId(), amount);
			return;
		}
		LifeLostEvent event = new LifeLostEvent(p, reason, amount);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (p.getHearts() - amount <= LifeStealMain.getInstance().getConfig().getInt("Defaults.HeartsZeroedAmount")) {
			p.resetHeartsAfterZeroed();
			LifeStealMain.getInstance().getConfig().getStringList("Defaults.CommandsAfterZeroing").forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
		} else {
			p.subHearts(amount);
			p.sendMessage(tl("HeartsLost", amount, p.getHearts()));
			p.updateTABColor();
			LifeStealMain.getInstance().getConfig().getStringList("Defaults.CommandsAfterDeath").forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
		}
	}
	
	public default void gainLife(Player player, double amount, LifeReason reason) {
		if (!LifeStealMain.getInstance().getRegionHook().shouldLoseHeartInRegion(player)) return;
		LifeStealPlayer p = LifeStealServer.a().getPlayer(player);
		if (player == null || !player.isOnline() || p == null) {
			gainLifeOffline(player.getUniqueId(), amount);
			return;
		}
		LifeGainEvent event2 = new LifeGainEvent(p, reason, amount);
		Bukkit.getServer().getPluginManager().callEvent(event2);
		if ((p.getHearts() + amount) > LifeStealMain.getInstance().getConfig().getInt("Defaults.MaxHeartAmount")) {
			if (LifeStealMain.getInstance().getConfig().getBoolean("Defaults.IfAtMaxHeartsGiveCanaster")) {
				player.getInventory().addItem(LifeStealMain.getInstance().getCanaster().clone());
				player.sendMessage(tl("HeartCanasterRecieved"));
				return;
			}
			p.sendMessage(tl("MaxHearts", amount, p.getHearts()));
			LifeStealMain.getInstance().getConfig().getStringList("Defaults.CommandsAfterKill").forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
			return;
		} else {
			p.addHearts(amount);
			p.sendMessage(tl("HeartsGained", amount, p.getHearts()));
			p.updateTABColor();
			LifeStealMain.getInstance().getConfig().getStringList("Defaults.CommandsAfterKill").forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replaceAll("%player%", p.getHandle().getName())));
		}
	}
	
	public default void gainLifeOffline(UUID player, double amount) {
		LifeStealServer.a().getDatabase().retrieveHearts(player.toString(), (retrieved) -> {
			LifeStealServer.a().getDatabase().storeHearts(player.toString(), retrieved + amount);
		});
	}
	
	public default void loseLifeOffline(UUID player, double amount) {
		LifeStealServer.a().getDatabase().retrieveHearts(player.toString(), (retrieved) -> {
			LifeStealServer.a().getDatabase().storeHearts(player.toString(), retrieved - amount);
		});
	}

	public default FileConfiguration getConfig() {
		return LifeStealMain.getInstance().getConfig();
	}
}
