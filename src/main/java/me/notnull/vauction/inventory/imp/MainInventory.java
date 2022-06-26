package me.notnull.vauction.inventory.imp;

import com.hakan.inventoryapi.inventory.ClickableItem;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.inventory.InventoryManager;
import me.notnull.vauction.inventory.InventoryProvider;

public class MainInventory implements InventoryProvider {

    private final Settings settings;
    private final InventoryManager inventoryManager;

    public MainInventory(final Settings settings, final InventoryManager inventoryManager){
        this.settings = settings;
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void init(Object object) {

        ClickableItem storage;
        ClickableItem auctions;
    }

    @Override
    public void update(Object object) {

    }
}
