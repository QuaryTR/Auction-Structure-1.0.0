package com.hakan.inventoryapi.customevents;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HInventoryClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;
    private final InventoryClickEvent event;
    private boolean cancelled = false;

    private final AuctionPlayer auctionPlayer;

    public HInventoryClickEvent(Player player, HInventory hInventory, InventoryClickEvent event, AuctionPlayer auctionPlayer) {
        this.player = player;
        this.hInventory = hInventory;
        this.event = event;
        this.auctionPlayer = auctionPlayer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public AuctionPlayer getAuctionPlayer(){return this.auctionPlayer;}

    public HInventory getInventory() {
        return this.hInventory;
    }

    public InventoryClickEvent getClickEvent() {
        return this.event;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}