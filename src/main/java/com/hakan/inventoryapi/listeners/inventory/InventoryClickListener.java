package com.hakan.inventoryapi.listeners.inventory;

import com.hakan.inventoryapi.InventoryAPI;
import com.hakan.inventoryapi.customevents.HInventoryClickEvent;
import com.hakan.inventoryapi.inventory.ClickableItem;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.listeners.ListenerAdapter;
import me.notnull.vauction.database.DatabaseManager;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class InventoryClickListener extends ListenerAdapter {

    private final DatabaseManager databaseManager;

    public InventoryClickListener(InventoryAPI inventoryAPI, DatabaseManager databaseManager) {
        super(inventoryAPI);
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN)) {
            event.setCancelled(true);
            return;
        } else if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            HInventory hInventory = this.inventoryManager.getPlayerInventory(player);

            if (hInventory == null) {
                return;
            } else if (!hInventory.isClickable() && event.getClickedInventory().equals(player.getInventory())) {
                event.setCancelled(true);
                return;
            } else if (hInventory.isClickable() && event.getClickedInventory().equals(player.getInventory())) {
                return;
            }

            AuctionPlayer auctionPlayer = databaseManager.getAuctionPlayer(player);

            HInventoryClickEvent hInventoryClickEvent = new HInventoryClickEvent(player, hInventory, event,auctionPlayer);
            Bukkit.getPluginManager().callEvent(hInventoryClickEvent);
            if (hInventoryClickEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            ClickableItem clickableItem = hInventory.getItem(event.getSlot());
            if (clickableItem != null) {
                event.setCancelled(true);
                Consumer<HInventoryClickEvent> clickEventConsumer = clickableItem.getClick();
                if (clickEventConsumer != null) {
                    clickEventConsumer.accept(hInventoryClickEvent);
                }
            }
        }
    }
}
