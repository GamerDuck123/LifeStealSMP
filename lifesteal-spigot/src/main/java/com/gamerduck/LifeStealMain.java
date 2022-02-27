package com.gamerduck;

import org.bukkit.NamespacedKey;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.gamerduck.commands.LifeStealCommand;
import com.gamerduck.configs.values;
import com.gamerduck.crafting.HeartCanaster;
import com.gamerduck.crafting.HeartCanasterUse;
import com.gamerduck.objects.LifeStealServer;

public class LifeStealMain extends JavaPlugin {
	
	public static LifeStealMain instance;
	NamespacedKey key;

	@Override
	public void onEnable() {
		instance = this;
		LifeStealAPI.a().onEnable(this);
		TabExecutor cmd = new LifeStealCommand();
		getCommand("lifesteal").setExecutor(cmd);
		getCommand("lifesteal").setTabCompleter(cmd);
		if (values.HEARTCANASTER_ENABLED) {
			key = new NamespacedKey(LifeStealMain.a(), "heart_canaster");
			new HeartCanaster();
			getServer().getPluginManager().registerEvents(new HeartCanasterUse(), this);
		}
	}
	
	@Override
	public void onDisable() {
		LifeStealAPI.a().onDisable(this);
	}
	
	public static LifeStealMain a() {return instance;}
	public LifeStealAPI getAPI() {return LifeStealAPI.a();}
	public LifeStealServer getLifeStealServer() {return LifeStealAPI.a().getServer();}
	public NamespacedKey getCanasterKey() {return key;}
}
