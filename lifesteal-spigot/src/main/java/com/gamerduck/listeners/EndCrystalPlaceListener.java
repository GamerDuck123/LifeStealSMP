package com.gamerduck.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.gamerduck.LifeStealMain;

public class EndCrystalPlaceListener implements Listener {
	final LifeStealMain plugin;
	public EndCrystalPlaceListener(LifeStealMain plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlace(PlayerInteractEvent e) {
        if (Action.RIGHT_CLICK_BLOCK == e.getAction()) {
            if (Material.OBSIDIAN == e.getClickedBlock().getType()) {
                if (Material.END_CRYSTAL == e.getMaterial()) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
	                	 List<Entity> entities = e.getPlayer().getNearbyEntities(4, 4, 4);
	                     for (Entity entity : entities) {
	                         if (EntityType.ENDER_CRYSTAL == entity.getType()) {
	                             EnderCrystal crystal = (EnderCrystal) entity;
	                             Block belowCrystal = crystal.getLocation().getBlock().getRelative(BlockFace.DOWN);
	                             if (e.getClickedBlock().equals(belowCrystal)) { 
	                                 crystal.setMetadata("PLAYER_PLACED", new FixedMetadataValue(plugin, e.getPlayer()));
	                            	 break;
	                             }
	                         }
	                     }
                    });
                }
            }
        }
	}
}
