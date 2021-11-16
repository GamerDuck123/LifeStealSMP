package com.gamerduck;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.gamerduck.commands.LifeStealCommand;
import com.gamerduck.objects.LifeStealServer;

public class LifeStealMain extends JavaPlugin {
	
	public static LifeStealMain instance;

	@Override
	public void onEnable() {
		instance = this;
		LifeStealAPI.a().onEnable(this);
		TabExecutor cmd = new LifeStealCommand();
		getCommand("lifesteal").setExecutor(cmd);
		getCommand("lifesteal").setTabCompleter(cmd);
	}
	
	@Override
	public void onDisable() {
		LifeStealAPI.a().onDisable(this);
	}
	
	public static LifeStealMain a() {return instance;}
	public LifeStealAPI getAPI() {return LifeStealAPI.a();}
	public LifeStealServer getLifeStealServer() {return LifeStealAPI.a().getServer();}
}
