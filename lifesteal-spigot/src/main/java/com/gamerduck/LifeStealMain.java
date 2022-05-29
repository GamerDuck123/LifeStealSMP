package com.gamerduck;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gamerduck.commands.LifeStealCommand;
import com.gamerduck.commands.WithdrawCommand;
import com.gamerduck.configs.FileResClassLoader;
import com.gamerduck.configs.UTF8PropertiesControl;
import com.gamerduck.crafting.HeartCanaster;
import com.gamerduck.crafting.HeartCanasterUse;
import com.gamerduck.listeners.DeathListener;
import com.gamerduck.listeners.LastDamagerListener;
import com.gamerduck.listeners.ScaleDamageListener;
import com.gamerduck.objects.LifeStealServer;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class LifeStealMain extends JavaPlugin {
	
	@Getter static LifeStealMain instance;
	@Getter NamespacedKey canasterKey;
	@Getter LifeStealServer lifeStealServer;
	File file;
	@Getter FileConfiguration config;
	@Getter HeartCanaster canaster;
	@Getter ResourceBundle messagesBundle;
	
	@Override
	public void onEnable() {
		instance = this;
		loadConfigs();
		lifeStealServer = new LifeStealServer(getServer(), this, config, messagesBundle);
		TabExecutor cmd = new LifeStealCommand();
		getCommand("lifesteal").setExecutor(cmd);
		getCommand("lifesteal").setTabCompleter(cmd);
		canasterKey = new NamespacedKey(LifeStealMain.a(), "heart_canaster");
		canaster = new HeartCanaster(config);
		getServer().getPluginManager().registerEvents(new HeartCanasterUse(config), this);
		if (config.getBoolean("Defaults.ShouldWithdrawCommandExist")) getCommand("withdraw").setExecutor(new WithdrawCommand(config));
		if (config.getBoolean("HeartCanaster.RecipeEnabled")) canaster.loadRecipe(config);
		if (config.getBoolean("Defaults.ShouldDamageScaleWithHealth")) getServer().getPluginManager().registerEvents(new ScaleDamageListener(), this);
		if (config.getBoolean("Defaults.ShouldKeepLastPlayerAsDamager")) getServer().getPluginManager().registerEvents(new LastDamagerListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(config), this);
	}
	
	@Override
	public void onDisable() {
		lifeStealServer.onDisable(this);
	}
	
	public static LifeStealMain a() {return instance;}
	
	private void loadConfigs() {
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdir();
			saveResource("config.yml", false);
		}
		config = new YamlConfiguration();
		try { config.load(configFile); }
		 catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }

		File messagesFile = new File(getDataFolder(), "messages.properties");
		if (!messagesFile.exists()) {
			messagesFile.getParentFile().mkdir();
			saveResource("messages.properties", false);
		}
		messagesBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH, new FileResClassLoader(this.getClass().getClassLoader(), this), new UTF8PropertiesControl());
	}
	
	public void saveConfig() {
		try {config.save(file);}
		catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");}
	}
	public void reloadConfig() {config = YamlConfiguration.loadConfiguration(file);}
	
}
