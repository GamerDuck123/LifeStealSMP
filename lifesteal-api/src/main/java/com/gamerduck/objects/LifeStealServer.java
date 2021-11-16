package com.gamerduck.objects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.gamerduck.LifeStealAPI;
import com.gamerduck.configs.ConfigYAML;
import com.gamerduck.configs.values;

public class LifeStealServer {
	ArrayList<LifeStealPlayer> players;
	Server server;
	ConfigYAML config;
	
	public LifeStealServer(Server server) {
		this.server = server;
		players = new ArrayList<LifeStealPlayer>();
	}
	
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
		LifeStealAPI.a().reloadConfig();
		LifeStealAPI.a().saveConfig();
		values.load(LifeStealAPI.a().getConfig());
	}
	
	
	public Server getServer() {return server;}
	
}
