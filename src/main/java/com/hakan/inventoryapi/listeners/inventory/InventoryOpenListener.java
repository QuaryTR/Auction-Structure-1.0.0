package com.hakan.inventoryapi.listeners.inventory;

import com.hakan.inventoryapi.InventoryAPI;
import com.hakan.inventoryapi.customevents.HInventoryOpenEvent;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.listeners.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener extends ListenerAdapter {

    public InventoryOpenListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            HInventory hInventory = this.inventoryManager.getPlayerInventory(player);
            if (hInventory == null) {
                return;
            }

            HInventoryOpenEvent hInventoryOpenEvent = new HInventoryOpenEvent(player, hInventory, event);
            Bukkit.getPluginManager().callEvent(hInventoryOpenEvent);
            event.setCancelled(hInventoryOpenEvent.isCancelled());
        }
    }
}
