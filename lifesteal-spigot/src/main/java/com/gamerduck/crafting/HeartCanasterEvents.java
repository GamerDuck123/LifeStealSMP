package com.gamerduck.crafting;

import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;
import com.gamerduck.commons.items.DuckItem;
import com.gamerduck.events.HeartCanasterUseEvent;
import com.gamerduck.objects.LifeStealPlayer;

public class HeartCanasterEvents implements Listener, GlobalMethods {
	
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		ItemMeta meta = e.getItem().getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (container.has(LifeStealMain.a().getCanasterKey(), PersistentDataType.STRING)) {
			if (container.get(LifeStealMain.a().getCanasterKey(), PersistentDataType.STRING).equalsIgnoreCase("heart_canaster")) {
				LifeStealPlayer p = LifeStealMain.a().getLifeStealServer().getPlayer(e.getPlayer());
				if (e.getAction() == Action.RIGHT_CLICK_AIR
						|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					HeartCanasterUseEvent event = new HeartCanasterUseEvent(p);
					Bukkit.getServer().getPluginManager().callEvent(event);
					if (event.isCancelled()) return;
					e.setCancelled(true);
					if ((p.getHearts() + getConfig().getDouble("HeartCanaster.AmountGive")) > getConfig().getDouble("Defaults.MaxHeartAmount")) {
						p.sendMessage(tl("MaxHearts"));
					}  else {
						if (e.getItem().getAmount() == 1) e.getItem().setAmount(0);
						else e.getItem().setAmount(e.getItem().getAmount() - 1);
						p.addHearts(getConfig().getDouble("HeartCanaster.AmountGive"));
						p.sendMessage(tl("HeartsGained", getConfig().getDouble("Defaults.MaxHeartAmount"), p.getHearts()));
					}
				}
			}
		}
	}
//	@EventHandler
//	public void onCraft(PrepareItemCraftEvent e) {
//		if (compare(e.getInventory().getStorageContents())) {
//			e.getInventory().setResult(new DuckItem().withMaterial(Material.STONE));
//		}
//	}
//	
//	public boolean compare(ItemStack[] contents) {
//        ItemStack[] flattened = Stream.of(LifeStealMain.getInstance().getCanaster().getCompareInv().getInv().getStorageContents())
//        				.toArray(ItemStack[]::new);
//        if(contents.length != flattened.length) return false;
//        boolean allMatch = true;
//        for (int i = 0; i < flattened.length; i++) {
//            if (!contents[i].isSimilar(flattened[i])) {
//                allMatch = false;
//            }
//        }
//        return allMatch;
//    }
//	public void onCraft(PrepareItemCraftEvent e) {
//		for (int i = 1; i <= 9; i++) {
//			ItemStack compareItem = LifeStealMain.getInstance().getCanaster().getCompareInv().getInv().getItem(i);
//			ItemStack actualItem = e.getInventory().getItem(i);
//			ItemMeta compareItemMeta = compareItem.getItemMeta();
//			ItemMeta actualItemMeta = actualItem.getItemMeta();
//			if (compareItem.getType() != null && compareItem.getType() == actualItem.getType()) return; 
//			if (compareItemMeta.getDisplayName() != null && compareItemMeta.getDisplayName().equalsIgnoreCase(actualItemMeta.getDisplayName())) return; 
//			if (compareItemMeta.getCustomModelData() == -1 && compareItemMeta.getCustomModelData() == actualItemMeta.getCustomModelData()) return; 
//		}
//	}
//	itemsInOrder.add(new DuckItem()
//			.withMaterial(section.isBlank() ? null : Material.getMaterial(path))
//			.withDisplayName(config.getString(path + "DisplayName").isBlank() ? null : config.getString(path + "DisplayName"))
//			.withLore(config.getStringList(path + "Lore").isEmpty() ? null : config.getStringList(path + "Lore"))
//			.withCustomModelData(config.getInt(path + "CustomModelData") == 0 ? null : config.getInt(path + "CustomModelData")));
}
