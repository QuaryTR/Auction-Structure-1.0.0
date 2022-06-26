package me.notnull.vauction.storage;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.player.AuctionPlayer;
import me.notnull.vauction.storage.utils.Product;

public interface Storage {

    StorageProvider getProvider();

    AuctionPlayer getPlayer();

    HInventory getInventory();

    void addProduct(final Product product);

    void removeProduct(final Product product);

    void setInventory(final HInventory inventory);


}
