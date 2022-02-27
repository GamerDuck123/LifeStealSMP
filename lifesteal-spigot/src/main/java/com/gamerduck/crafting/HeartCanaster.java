package com.gamerduck.crafting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.gamerduck.LifeStealMain;
import com.gamerduck.configs.values;

public class HeartCanaster extends ItemStack {
	public HeartCanaster() {
		super(Material.matchMaterial(values.HEARTCANASTER_ITEM_MATERIAL), values.HEARTCANASTER_ITEM_AMOUNT);
		
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(values.HEARTCANASTER_ITEM_DISPLAYNAME);
		meta.setLore(values.HEARTCANASTER_ITEM_LORE);
		meta.getPersistentDataContainer().set(LifeStealMain.a().getCanasterKey(), PersistentDataType.STRING, "heart_canaster");
		setItemMeta(meta);
		
		Recipe recipe = null;
		switch(values.HEARTCANASTER_RECIPE_TYPE.toUpperCase()) {
			case "SHAPED":
				recipe = new ShapedRecipe(LifeStealMain.a().getCanasterKey(), this);
				((ShapedRecipe) recipe).shape(values.HEARTCANASTER_RECIPE_SHAPE.get(0), 
						  values.HEARTCANASTER_RECIPE_SHAPE.get(1), 
						  values.HEARTCANASTER_RECIPE_SHAPE.get(2));
				for (String contents : values.HEARTCANASTER_RECIPE_CONTENTS) {
					String[] content = contents.split(":");
					if (Material.matchMaterial(content[1]) != null) {
						((ShapedRecipe) recipe).setIngredient(content[0].charAt(0), Material.matchMaterial(content[1]));
					}
				}
				break;
			case "SHAPELESS":
				recipe = new ShapelessRecipe(LifeStealMain.a().getCanasterKey(), this);
				for (String contents : values.HEARTCANASTER_RECIPE_CONTENTS) {
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
