package com.gamerduck.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gamerduck.objects.LifeStealPlayer;

public class HeartCanasterUseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
	public LifeStealPlayer player;
	public boolean canceled;
	
	public HeartCanasterUseEvent(LifeStealPlayer player) {
		this.player = player;
		this.canceled = false;
	}
	
	public LifeStealPlayer getPlayer() {
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
