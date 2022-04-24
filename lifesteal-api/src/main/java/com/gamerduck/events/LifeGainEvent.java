package com.gamerduck.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gamerduck.enums.LifeReason;
import com.gamerduck.objects.LifeStealPlayer;

public class LifeGainEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
	public LifeStealPlayer player;
	public LifeReason reason;
	public Double amount;
	public boolean canceled;
	
	public LifeGainEvent(LifeStealPlayer player, LifeReason reason, Double amount) {
		this.player = player;
		this.reason = reason;
		this.amount = amount;
		this.canceled = false;
	}
	
	public LifeStealPlayer getPlayer() {
		return player;
	}
	
	public LifeReason getReason() {
		return reason;
	}
	
	public Double getAmount() {
		return amount;
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
