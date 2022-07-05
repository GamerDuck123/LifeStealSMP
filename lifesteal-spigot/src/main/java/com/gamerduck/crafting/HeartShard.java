package com.gamerduck.crafting;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

public class HeartShard extends ItemStack implements GlobalMethods {	
	
	public HeartShard(FileConfiguration config) {
		super(Material.matchMaterial(config.getString("HeartShard.Item.Material")), 1);
		ItemMeta meta = super.getItemMeta();
		if (config.contains("HeartShard.Item.DisplayName")) meta.setDisplayName(color(config.getString("HeartShard.Item.DisplayName")));
		if (config.contains("HeartShard.Item.Lore")) meta.setLore(color(config.getStringList("HeartShard.Item.Lore")));
		if (config.contains("HeartShard.Item.CustomModelData")) meta.setCustomModelData(config.getInt("HeartShard.Item.CustomModelData"));
		
		super.setItemMeta(meta);
	
	}
	public void loadRecipe(FileConfiguration config) {
		Recipe recipe = null;		
		List<String> shapeList = config.getStringList("HeartShard.Recipe.Shape");
		switch(config.getString("HeartShard.Recipe.Type").toUpperCase()) {
			case "SHAPED":
				recipe = new ShapedRecipe(new NamespacedKey(LifeStealMain.a(), "lifesteal_heartshard"), this);
				((ShapedRecipe) recipe).shape(shapeList.get(0), shapeList.get(1), shapeList.get(2));
				for (String section : config.getConfigurationSection("HeartShard.Recipe.Contents").getKeys(false)) {
					String path = "HeartShard.Recipe.Contents." + section + ".";
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
				recipe = new ShapelessRecipe(new NamespacedKey(LifeStealMain.a(), "lifesteal_heartshard"), this);
				for (String section : config.getConfigurationSection("HeartShard.Recipe.Contents").getKeys(false)) {
					String path = "HeartShard.Recipe.Contents." + section + ".";
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
