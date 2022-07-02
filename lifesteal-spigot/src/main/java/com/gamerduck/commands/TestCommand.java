package com.gamerduck.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;
import com.gamerduck.commons.items.DuckItem;

public class TestCommand implements CommandExecutor, GlobalMethods {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration config = LifeStealMain.a().getConfig();
		for (String section : config.getConfigurationSection("HeartCanaster.Recipe.Contents").getKeys(false)) {
			String path = "HeartCanaster.Recipe.Contents." + section + ".";
			System.out.println(section);
			DuckItem item = new DuckItem().withMaterial(Material.getMaterial(section));
			if (config.contains(path + "DisplayName")) item.withDisplayName(config.getString(path + "DisplayName"));
			if (config.contains(path + "Lore")) item.withLore(config.getStringList(path + "Lore"));
			if (config.contains(path + "CustomModelData")) item.withCustomModelData(config.getInt(path + "CustomModelData"));
			((Player) sender).getInventory().addItem(item);
		}
		return true;
	}
	

}
