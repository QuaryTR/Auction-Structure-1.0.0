package com.hakan.inventoryapi.listeners.bukkit;

import com.hakan.inventoryapi.InventoryAPI;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.listeners.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Map;

public class PluginDisableListener extends ListenerAdapter {

    public PluginDisableListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin)) {
            Map<String, HInventory> playerInventoryMap = this.inventoryManager.getPlayerInventoryMap();
            if (playerInventoryMap == null || playerInventoryMap.isEmpty()) {
                return;
            }

            for (Map.Entry<String, HInventory> entry : playerInventoryMap.entrySet()) {
                Player player = Bukkit.getPlayerExact(entry.getKey());
                HInventory hInventory = entry.getValue();
                if (player == null) {
                    return;
                }
                if (hInventory != null && playerInventoryMap.get(player.getName()) != null) {
                    hInventory.close(player);
                }
            }
        }
    }
}