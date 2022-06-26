package com.hakan.inventoryapi.inventory;

import com.hakan.inventoryapi.InventoryAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class   InventoryManager {

    private InventoryAPI inventoryAPI;
    private final Plugin plugin;
    private final Map<String, HInventory> playerInventoryMap = new HashMap<>();

    public InventoryManager(InventoryAPI inventoryAPI) {
        this.inventoryAPI = inventoryAPI;
        this.plugin = inventoryAPI.getPlugin();
    }

    public Map<String, HInventory> getPlayerInventoryMap() {
        return this.playerInventoryMap;
    }

    public HInventory getPlayerInventory(String playerName) {
        return this.playerInventoryMap.get(playerName);
    }

    public HInventory getPlayerInventory(Player player) {
        return this.getPlayerInventory(player.getName());
    }

    public void setPlayerInventory(String playerName, HInventory hInventory) {
        this.playerInventoryMap.put(playerName, hInventory);
    }

    public void setPlayerInventory(Player player, HInventory hInventory) {
        this.setPlayerInventory(player.getName(), hInventory);
    }

    public String getId(String playerName) {
        HInventory hInventory = this.getPlayerInventory(playerName);
        return hInventory != null ? hInventory.getId() : null;
    }

    public String getId(Player player) {
        return this.getId(player.getName());
    }
}