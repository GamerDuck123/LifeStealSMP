package com.gamerduck.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gamerduck.enums.LifeReason;

public class HeartCanasterCraftEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
	public Player player;
	public boolean canceled;
	
	public HeartCanasterCraftEvent(Player player) {
		this.player = player;
		this.canceled = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		canceled = cancel;
	}

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
