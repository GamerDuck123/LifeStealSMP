package com.gamerduck.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class ConfigYAML {
	File file;
	FileConfiguration config;
	
	public ConfigYAML(Plugin pl) {
		if (!pl.getDataFolder().exists()) { pl.getDataFolder().mkdir(); }
		 file = new File(pl.getDataFolder(), "config.yml");
		 if (!file.exists()) {pl.saveResource("config.yml", false);}
		 config = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig() {return config;}
	public boolean saveConfig() {
		try {config.save(file);return true;
        }catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");return false;}
	}
	public void reloadConfig() {config = YamlConfiguration.loadConfiguration(file);}
}
