package me.notnull.vauction.storage.imp;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.player.AuctionPlayer;
import me.notnull.vauction.storage.Storage;
import me.notnull.vauction.storage.StorageProvider;
import me.notnull.vauction.storage.utils.Product;

public class AuctionStorage implements Storage {

    private HInventory inventory;
    private final StorageProvider provider;
    private final AuctionPlayer player;

    public AuctionStorage(final StorageProvider provider,final AuctionPlayer player){

        this.player = player;
        this.provider = provider;
        this.inventory = null;
    }


    @Override
    public StorageProvider getProvider() {
        return provider;
    }

    @Override
    public AuctionPlayer getPlayer() {
        return player;
    }

    @Override
    public HInventory getInventory() {
        return inventory;
    }

    @Override
    public void addProduct(Product product) {
        provider.addProductToInventory(product,inventory);
    }

    @Override
    public void removeProduct(Product product) {
        provider.removeProductFromInventory(product,inventory);
    }

    @Override
    public void setInventory(HInventory inventory){ this.inventory = inventory;}
}
