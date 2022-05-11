package com.gamerduck;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gamerduck.commands.LifeStealCommand;
import com.gamerduck.commands.WithdrawCommand;
import com.gamerduck.configs.values;
import com.gamerduck.crafting.HeartCanaster;
import com.gamerduck.crafting.HeartCanasterUse;
import com.gamerduck.listeners.DeathListener;
import com.gamerduck.listeners.LastDamagerListener;
import com.gamerduck.listeners.ScaleDamageListener;
import com.gamerduck.objects.LifeStealServer;

import net.md_5.bungee.api.ChatColor;

public class LifeStealMain extends JavaPlugin {
	
	public static LifeStealMain instance;
	NamespacedKey key;
	LifeStealServer server;
	File file;
	FileConfiguration config;
	HeartCanaster canaster;
	@Override
	public void onEnable() {
		instance = this;
		server = new LifeStealServer(getServer(), this, loadConfig());
		TabExecutor cmd = new LifeStealCommand();
		getCommand("lifesteal").setExecutor(cmd);
		getCommand("lifesteal").setTabCompleter(cmd);
		if (values.SHOULD_WITHDRAW_COMMAND_EXIST) {
			key = new NamespacedKey(LifeStealMain.a(), "heart_canaster");
			canaster = new HeartCanaster();
			canaster.setRecipe();
			getCommand("withdraw").setExecutor(new WithdrawCommand());
			getServer().getPluginManager().registerEvents(new HeartCanasterUse(), this);
		}
		if (values.HEARTCANASTER_ENABLED) {
			if (canaster == null) {
				key = new NamespacedKey(LifeStealMain.a(), "heart_canaster");
				canaster = new HeartCanaster();
				canaster.setRecipe();
				getServer().getPluginManager().registerEvents(new HeartCanasterUse(), this);
			}
		}
		if (!values.SHOULD_DAMAGE_SCALE_WITH_HEALTH) getServer().getPluginManager().registerEvents(new ScaleDamageListener(), this);
		if (values.SHOULD_KEEP_LAST_PLAYER_AS_DAMAGER) getServer().getPluginManager().registerEvents(new LastDamagerListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(), this);
	}
	
	@Override
	public void onDisable() {
		server.onDisable(this);
	}
	
	public static LifeStealMain a() {return instance;}
	public LifeStealServer getLifeStealServer() {return server;}
	public NamespacedKey getCanasterKey() {return key;}
	public HeartCanaster getCanaster() {return canaster;}
	
	private FileConfiguration loadConfig() {
		if (!getDataFolder().exists()) { getDataFolder().mkdir(); }
		 file = new File(getDataFolder(), "config.yml");
		 if (!file.exists()) {saveResource("config.yml", false);}
		 return config = YamlConfiguration.loadConfiguration(file);
	}
	public FileConfiguration getConfig() {return config;}
	public void saveConfig() {
		try {config.save(file);}
		catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");}
	}
	public void reloadConfig() {config = YamlConfiguration.loadConfiguration(file);}
	
}
