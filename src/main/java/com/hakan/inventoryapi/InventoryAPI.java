package com.hakan.inventoryapi;

import com.hakan.inventoryapi.inventory.InventoryManager;
import com.hakan.inventoryapi.listeners.bukkit.PlayerQuitListener;
import com.hakan.inventoryapi.listeners.bukkit.PluginDisableListener;
import com.hakan.inventoryapi.listeners.inventory.InventoryClickListener;
import com.hakan.inventoryapi.listeners.inventory.InventoryCloseListener;
import com.hakan.inventoryapi.listeners.inventory.InventoryOpenListener;
import me.notnull.vauction.database.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class InventoryAPI {

    private static InventoryAPI instance;

    private final Plugin plugin;
    private final InventoryManager inventoryManager;

    private InventoryAPI(Plugin plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.inventoryManager = new InventoryManager(this);

        if (InventoryAPI.instance == null) {
            InventoryAPI.instance = this;

            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new InventoryClickListener(this,databaseManager), plugin);
            pm.registerEvents(new InventoryCloseListener(this), plugin);
            pm.registerEvents(new InventoryOpenListener(this), plugin);
            pm.registerEvents(new PlayerQuitListener(this), plugin);
            pm.registerEvents(new PluginDisableListener(this), plugin);
        }
    }

    public static InventoryAPI getInstance(Plugin plugin,DatabaseManager databaseManager) {
        return InventoryAPI.instance == null ? new InventoryAPI(plugin,databaseManager) : InventoryAPI.instance;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public InventoryCreator getInventoryCreator() {
        return new InventoryCreator(this);
    }
}