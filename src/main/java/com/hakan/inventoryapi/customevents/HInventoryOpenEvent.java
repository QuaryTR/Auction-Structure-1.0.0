package com.hakan.inventoryapi.customevents;

import com.hakan.inventoryapi.inventory.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class HInventoryOpenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;
    private final InventoryOpenEvent event;
    private boolean cancelled = false;

    public HInventoryOpenEvent(Player player, HInventory hInventory, InventoryOpenEvent event) {
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

    public InventoryOpenEvent getOpenEvent() {
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