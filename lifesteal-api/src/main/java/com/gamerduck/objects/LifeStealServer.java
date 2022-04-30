package com.gamerduck.objects;

import java.util.ArrayList;
import java.util.UUID;

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
import com.gamerduck.configs.values;

public class LifeStealServer {
	public static LifeStealServer instance;
	public static LifeStealServer a() {return instance;}
	ArrayList<LifeStealPlayer> players;
	Server server;
	FileConfiguration config;
	Plugin pl;
	Database db;
	
	public LifeStealServer(Server server, Plugin pl, FileConfiguration config) {
		instance = this;
		this.server = server;
		this.pl = pl;
		this.config = config;
		players = new ArrayList<LifeStealPlayer>();
		values.load(config);
		if (values.MYSQL_ENABLED) {
			try {db = new Database(pl, 
					values.MYSQL_AUTORECONNECT,
					values.MYSQL_HOST,
					values.MYSQL_DATABASE,
					values.MYSQL_USERNAME,
					values.MYSQL_PASSWORD,
					values.MYSQL_PORT);
			} catch (Exception e) {e.printStackTrace();}
		} else {
			try {db = new Database(pl);
			} catch (Exception e) {e.printStackTrace();}
		}
		pl.getServer().getPluginManager().registerEvents(new Events(), pl);
	}
	
	public void onDisable(Plugin pl) {
		Bukkit.getServer().getOnlinePlayers().forEach(p -> {
			db.storeHearts(p.getUniqueId().toString(), p.getHealthScale());
		});
		db.close();
	}	

	public Database getDatabase() {return db;}
	
	public ArrayList<LifeStealPlayer> getOnlinePlayers() {return players;}
	public boolean addOnlinePlayer(Player player) {
		return players.add(new LifeStealPlayer(player));
	}
	public boolean removeOnlinePlayer(LifeStealPlayer player) {
		return players.remove(player);
	}
	public LifeStealPlayer getPlayer(String name) {
		LifeStealPlayer player = null;
		for (LifeStealPlayer p : players) {
			if (p.getName().equalsIgnoreCase(name)) {
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
			if (p.getPlayer() == play) {
				player = p;
			}
		}
		return player;
	}
	
	public void reload() {
		values.load(config);
	}
	public Plugin getPlugin() {
		return pl;
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
