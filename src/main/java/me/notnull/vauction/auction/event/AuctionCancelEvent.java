package me.notnull.vauction.auction.event;

import me.notnull.vauction.auction.Auction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AuctionCancelEvent extends Event implements Cancellable {

    private final Auction auction;
    private boolean cancelled;

    private static final HandlerList handlers = new HandlerList();

    public AuctionCancelEvent(final Auction auction){
        this.auction = auction;
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public static HandlerList getHandlerList(){return handlers;}

    @Override
    public HandlerList getHandlers() {return handlers;}

    public Auction getAuction(){
        return this.auction;
    }
}
