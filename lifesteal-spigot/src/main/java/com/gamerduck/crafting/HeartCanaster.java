package com.gamerduck.crafting;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;
import com.gamerduck.commons.items.DuckItem;

public class HeartCanaster extends ItemStack implements GlobalMethods {	
	
	public HeartCanaster(FileConfiguration config) {
		super(Material.matchMaterial(config.getString("HeartCanaster.Item.Material")), 1);
		ItemMeta meta = super.getItemMeta();
		meta.setDisplayName(color(config.getString("HeartCanaster.Item.DisplayName")));
		meta.setLore(config.getStringList(color("HeartCanaster.Item.Lore")));
		meta.setCustomModelData(config.getInt("HeartCanaster.Item.CustomModelData"));
		meta.getPersistentDataContainer().set(LifeStealMain.a().getLifeStealServer().getCanasterKey(), PersistentDataType.STRING, "heart_canaster");
		
		super.setItemMeta(meta);
	
	}
	public void loadRecipe(FileConfiguration config) {
		Recipe recipe = null;		
		List<String> shapeList = config.getStringList("HeartCanaster.Recipe.Shape");
		switch(config.getString("HeartCanaster.Recipe.Type").toUpperCase()) {
			case "SHAPED":
				recipe = new ShapedRecipe(LifeStealMain.a().getLifeStealServer().getCanasterKey(), this);
				((ShapedRecipe) recipe).shape(shapeList.get(0), shapeList.get(1), shapeList.get(2));
				for (String section : config.getConfigurationSection("HeartCanaster.Recipe.Contents").getKeys(false)) {
					String path = "HeartCanaster.Recipe.Contents." + section + ".";
					boolean justMaterial = true;
					DuckItem item = new DuckItem().withMaterial(Material.getMaterial(section));
					if (config.contains(path + "DisplayName")) {
						item.withDisplayName(config.getString(path + "DisplayName"));
						justMaterial = false;
					}
					if (config.contains(path + "Lore")) {
						item.withLore(config.getStringList(path + "Lore")); 
						justMaterial = false;
					}
					if (config.contains(path + "CustomModelData")) {
						item.withCustomModelData(config.getInt(path + "CustomModelData"));
						justMaterial = false;
					}
					if (!justMaterial) ((ShapedRecipe) recipe).setIngredient(config.getString(path + "Symbol").charAt(0), new RecipeChoice.ExactChoice(item));
					else ((ShapedRecipe) recipe).setIngredient(config.getString(path + "Symbol").charAt(0), item.getType());
				}
				break;
			case "SHAPELESS":
				recipe = new ShapelessRecipe(LifeStealMain.a().getLifeStealServer().getCanasterKey(), this);
				for (String section : config.getConfigurationSection("HeartCanaster.Recipe.Contents").getKeys(false)) {
					String path = "HeartCanaster.Recipe.Contents." + section + ".";
					boolean justMaterial = true;
					DuckItem item = new DuckItem().withMaterial(Material.getMaterial(section));
					if (config.contains(path + "DisplayName")) {
						item.withDisplayName(config.getString(path + "DisplayName"));
						justMaterial = false;
					}
					if (config.contains(path + "Lore")) {
						item.withLore(config.getStringList(path + "Lore")); 
						justMaterial = false;
					}
					if (config.contains(path + "CustomModelData")) {
						item.withCustomModelData(config.getInt(path + "CustomModelData"));
						justMaterial = false;
					}
					if (!justMaterial) ((ShapedRecipe) recipe).setIngredient(config.getString(path + "Symbol").charAt(0), new RecipeChoice.ExactChoice(item));
					else ((ShapedRecipe) recipe).setIngredient(config.getString(path + "Symbol").charAt(0), item.getType());
				}
				break;
		}
		if (recipe != null) LifeStealMain.a().getServer().addRecipe(recipe);
	}
}
