package com.gamerduck.crafting;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.gamerduck.LifeStealMain;
import com.gamerduck.configs.values;
import com.gamerduck.events.HeartCanasterUseEvent;
import com.gamerduck.objects.LifeStealPlayer;

public class HeartCanasterUse implements Listener {
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
					HeartCanasterUseEvent event = new HeartCanasterUseEvent(p.getPlayer());
					Bukkit.getServer().getPluginManager().callEvent(event);
					if (event.isCancelled()) return;
					e.setCancelled(true);
					p.addHearts(values.HEARTCANASTER_HEARTS_AMOUNT);
					p.sendMessage(values.MESSAGES_HEARTS_GAINED.
							replaceAll("%amount%", "" + values.HEARTCANASTER_HEARTS_AMOUNT)
							.replaceAll("%total%", "" + p.getHearts()));
				}
			}
		}
	}
}
