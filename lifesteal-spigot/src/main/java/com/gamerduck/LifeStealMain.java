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
import com.gamerduck.commons.files.Files;
import com.gamerduck.commons.files.UTF8PropertiesControl;
import com.gamerduck.crafting.HeartCanaster;
import com.gamerduck.crafting.HeartCanasterEvents;
import com.gamerduck.crafting.HeartShard;
import com.gamerduck.hooks.PlaceHolderAPIHook;
import com.gamerduck.hooks.region.DefaultHook;
import com.gamerduck.hooks.region.RegionHook;
import com.gamerduck.hooks.region.WorldGuardHook;
import com.gamerduck.listeners.DeathListener;
import com.gamerduck.listeners.EndCrystalPlaceListener;
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
	@Getter HeartShard shard;
	@Getter ResourceBundle messagesBundle;
	@Getter RegionHook regionHook;
	
	@Override
	public void onEnable() {
		instance = this;
		loadConfigs();
		lifeStealServer = new LifeStealServer(getServer(), this, config, messagesBundle);
		TabExecutor cmd = new LifeStealCommand();
//		getCommand("test").setExecutor(new TestCommand());
		getCommand("lifesteal").setExecutor(cmd);
		getCommand("lifesteal").setTabCompleter(cmd);
		canasterKey = new NamespacedKey(LifeStealMain.a(), "heart_canaster");
		canaster = new HeartCanaster(config);
		shard = new HeartShard(config);
		getServer().getPluginManager().registerEvents(new HeartCanasterEvents(), this);
		if (config.getBoolean("Defaults.ShouldWithdrawCommandExist")) getCommand("withdraw").setExecutor(new WithdrawCommand(config));
		if (config.getBoolean("HeartCanaster.RecipeEnabled")) canaster.loadRecipe(config);
		if (config.getBoolean("HeartShard.RecipeEnabled")) shard.loadRecipe(config);
		if (!config.getBoolean("Defaults.ShouldDamageScaleWithHealth")) getServer().getPluginManager().registerEvents(new ScaleDamageListener(), this);
		if (config.getBoolean("Defaults.ShouldKeepLastPlayerAsDamager")) getServer().getPluginManager().registerEvents(new LastDamagerListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(), this);
		getServer().getPluginManager().registerEvents(new EndCrystalPlaceListener(this), this);
		if (getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null) {
			new PlaceHolderAPIHook(lifeStealServer).register();
		}
		if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			regionHook = new WorldGuardHook().enable(this);
		} else {
			regionHook = new DefaultHook().enable(this);
		}
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
		} else {
			try {Files.updatePropertyFiles(this, "messages.properties", "messages.properties");}
			catch (IOException e) {e.printStackTrace();}
		}
		messagesBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH, getClass().getClassLoader(), new UTF8PropertiesControl());
	}

	
	public void saveConfig() {
		try {config.save(file);}
		catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");}
	}
	public void reloadConfig() {config = YamlConfiguration.loadConfiguration(file);}
	
}
