package com.gamerduck.crafting;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;
import com.gamerduck.events.HeartCanasterUseEvent;
import com.gamerduck.objects.LifeStealPlayer;

public class HeartCanasterEvents implements Listener, GlobalMethods {
	
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		ItemMeta meta = e.getItem().getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (container.has(LifeStealMain.a().getLifeStealServer().getCanasterKey(), PersistentDataType.STRING)) {
			if (container.get(LifeStealMain.a().getLifeStealServer().getCanasterKey(), PersistentDataType.STRING).equalsIgnoreCase("heart_canaster")) {
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
}
