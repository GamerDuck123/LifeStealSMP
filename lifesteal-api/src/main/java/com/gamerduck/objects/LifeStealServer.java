package com.gamerduck.objects;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.gamerduck.configs.Database;

import lombok.Getter;

public class LifeStealServer {
	public static LifeStealServer instance;
	public static LifeStealServer a() {return instance;}
	@Getter ArrayList<LifeStealPlayer> players;
	final Server server;
	@Getter final FileConfiguration config;
	@Getter final Plugin plugin;
	@Getter Database database;
	@Getter ResourceBundle messages;
    @Getter final ExecutorService executor;
	
	public LifeStealServer(Server server, Plugin plugin, FileConfiguration config, ResourceBundle messages) {
		instance = this;
		this.server = server;
		this.plugin = plugin;
		this.config = config;
        executor = Executors.newSingleThreadExecutor();
		players = new ArrayList<LifeStealPlayer>();
		if (config.getBoolean("MySQL.Enabled")) {
			try {database = new Database(plugin, 
					config.getBoolean("MySQL.AutoReconnect"),
					config.getString("MySQL.Host"),
					config.getString("MySQL.Database"),
					config.getString("MySQL.Username"),
					config.getString("MySQL.Password"),
					config.getInt("MySQL.Port"));
			} catch (Exception e) {e.printStackTrace();}
		} else {
			try {database = new Database(plugin);
			} catch (Exception e) {e.printStackTrace();}
		}
		plugin.getServer().getPluginManager().registerEvents(new Events(), plugin);
	}
	
	public void onDisable(Plugin pl) {
		Bukkit.getServer().getOnlinePlayers().forEach(p -> {
			database.storeHearts(p.getUniqueId().toString(), p.getHealthScale());
		});
		executor.shutdownNow().forEach(r -> r.run());
		database.close();
		
	}	
	
	public boolean addOnlinePlayer(Player player) {
		return players.add(new LifeStealPlayer(player));
	}
	public boolean removeOnlinePlayer(LifeStealPlayer player) {
		return players.remove(player);
	}
	public LifeStealPlayer getPlayer(String name) {
		LifeStealPlayer player = null;
		for (LifeStealPlayer p : players) {
			if (p.getHandle().getName().equalsIgnoreCase(name)) {
				player = p;
			}
		}
		return player;
	}
	public LifeStealPlayer getPlayer(UUID uuid) {
		LifeStealPlayer player = null;
		for (LifeStealPlayer p : players) {
			if (p.getUUID() == uuid) {
				player = p;
			}
		}
		return player;
	}
	public LifeStealPlayer getPlayer(Player play) {
		LifeStealPlayer player = null;
		for (LifeStealPlayer p : players) {
			if (p.getHandle() == play) {
				player = p;
			}
		}
		return player;
	}
	
}
class Events implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		LifeStealServer.a().addOnlinePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
			LifeStealServer.a().getPlayer(e.getPlayer().getName()).onQuit();
			LifeStealServer.a().removeOnlinePlayer(LifeStealServer.a().getPlayer(e.getPlayer().getName()));
		
	}
}
