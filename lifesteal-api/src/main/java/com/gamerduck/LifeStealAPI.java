package com.gamerduck;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.gamerduck.configs.ConfigYAML;
import com.gamerduck.configs.Database;
import com.gamerduck.configs.values;
import com.gamerduck.handlers.PlayerHeartsHandler;
import com.gamerduck.handlers.ServerHandlers;
import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

public class LifeStealAPI {
	
	public static LifeStealAPI instance = new LifeStealAPI();
	public LifeStealAPI() {}
	public static LifeStealAPI a() {return instance;}
	
	LifeStealServer LSserver;
	ConfigYAML config;
	Database db;
	
	public void onEnable(Plugin pl) {
		LSserver = new LifeStealServer(pl.getServer());	
		config = new ConfigYAML(pl);
		values.load(config.getConfig());
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
		pl.getServer().getPluginManager().registerEvents(new ServerHandlers(), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerHeartsHandler(), pl);
	}
	
	public void onDisable(Plugin pl) {
		Bukkit.getServer().getOnlinePlayers().forEach(p -> {
			db.storeHearts(p.getUniqueId().toString(), p.getHealthScale());
		});
		db.close();
	}
	
	public LifeStealServer getServer() {return LSserver;}
	
	public FileConfiguration getConfig() {return config.getConfig();}
	public boolean saveConfig() {return config.saveConfig();}
	public void reloadConfig() {config.reloadConfig();}

	public Database getDatabase() {return db;}
}
