package com.hakan.inventoryapi.customevents;

import com.hakan.inventoryapi.inventory.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class HInventoryCloseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;
    private final InventoryCloseEvent event;
    private boolean cancelled = false;

    public HInventoryCloseEvent(Player player, HInventory hInventory, InventoryCloseEvent event) {
        this.player = player;
        this.hInventory = hInventory;
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HInventory getInventory() {
        return this.hInventory;
    }

    public InventoryCloseEvent getCloseEvent() {
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