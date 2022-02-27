package com.gamerduck.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gamerduck.LifeStealMain;

public class DatabaseEditor {
	Inventory inv;
	ArrayList<String> playerUUIDs;
	HashMap<Integer, Page> pages;
	public DatabaseEditor() {
		inv = Bukkit.createInventory(null, 54);
		playerUUIDs = new ArrayList<String>();
		LifeStealMain.a().getAPI().getDatabase().getAllUUIDS().forEach(s -> playerUUIDs.add(s));
		pages = new HashMap<Integer, Page>();
		int amount = 0;
    	int page = 1;
    	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (String uuid : playerUUIDs) {
			ItemStack i = new ItemStack(Material.PLAYER_HEAD);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(uuid);
			i.setItemMeta(im);
			items.add(i);
			if (amount >= 45) {
    			pages.put(page, new Page(page, items));
    			amount = 0;
    			page++;
    			items = new ArrayList<ItemStack>();
    		}
		}
    	if (amount <= 45) {
			pages.put(page, new Page(page, items));
		}
	}
	
	public Inventory openPage(Integer page) {
		Inventory inv = Bukkit.createInventory(null, 54);
		pages.get(page).getItems().forEach(i -> {
			inv.addItem(i);
		});;
		return inv;
	}
	
}
