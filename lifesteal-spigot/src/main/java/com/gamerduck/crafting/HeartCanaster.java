package com.gamerduck.crafting;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.gamerduck.LifeStealMain;

public class HeartCanaster extends ItemStack {
	public HeartCanaster(FileConfiguration config) {
		super(Material.matchMaterial(config.getString("HeartCanaster.Item.Material")), 1);
		
		ItemMeta meta = super.getItemMeta();
		meta.setDisplayName(config.getString("HeartCanaster.Item.DisplayName"));
		meta.setLore(config.getStringList("HeartCanaster.Item.Lore"));
		meta.getPersistentDataContainer().set(LifeStealMain.a().getCanasterKey(), PersistentDataType.STRING, "heart_canaster");
		super.setItemMeta(meta);
	
	}
	
	public void loadRecipe(FileConfiguration config) {
		Recipe recipe = null;
		List<String> shapeList = config.getStringList("HeartCanaster.Recipe.Shape");
		List<String> contentsList = config.getStringList("HeartCanaster.Recipe.Contents");
		switch(config.getString("HeartCanaster.Recipe.Type").toUpperCase()) {
			case "SHAPED":
				recipe = new ShapedRecipe(LifeStealMain.a().getCanasterKey(), this);
				((ShapedRecipe) recipe).shape(shapeList.get(0), 
						shapeList.get(1), 
						  shapeList.get(2));
				for (String contents : contentsList) {
					String[] content = contents.split(":");
					if (Material.matchMaterial(content[1]) != null) {
						((ShapedRecipe) recipe).setIngredient(content[0].charAt(0), Material.matchMaterial(content[1]));
					}
				}
				break;
			case "SHAPELESS":
				recipe = new ShapelessRecipe(LifeStealMain.a().getCanasterKey(), this);
				for (String contents : contentsList) {
					String[] content = contents.split(":");
					if (Material.matchMaterial(content[1]) != null) {
						((ShapelessRecipe) recipe).addIngredient(Material.matchMaterial(content[1]));
					}
				}
				break;
		}
		if (recipe != null) LifeStealMain.a().getServer().addRecipe(recipe);
	}
}
