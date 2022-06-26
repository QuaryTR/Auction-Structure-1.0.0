package com.hakan.inventoryapi.listeners.inventory;

import com.hakan.inventoryapi.InventoryAPI;
import com.hakan.inventoryapi.customevents.HInventoryCloseEvent;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.listeners.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener extends ListenerAdapter {

    public InventoryCloseListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            HInventory hInventory = this.inventoryManager.getPlayerInventory(player);

            if (hInventory == null) {
                return;
            }

            if (hInventory.isClosable()) {
                HInventoryCloseEvent hInventoryCloseEvent = new HInventoryCloseEvent(player, hInventory, event);
                Bukkit.getPluginManager().callEvent(hInventoryCloseEvent);
                if (hInventoryCloseEvent.isCancelled() && this.plugin != null) {
                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> hInventory.open(player), 1);
                    return;
                }

                if (hInventory.closeChecker != null) {
                    hInventory.closeChecker.accept(event);
                }

                this.inventoryManager.getPlayerInventoryMap().remove(player.getName());
                if (this.plugin != null) {
                    Bukkit.getScheduler().runTaskLater(this.plugin, player::updateInventory, 1);
                }
            } else {
                if (this.plugin != null) {
                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> hInventory.open(player), 1);
                }
            }
        }
    }
}
