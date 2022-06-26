package me.notnull.vauction.storage;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.inventory.Pagination;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.player.AuctionPlayer;
import me.notnull.vauction.storage.utils.Product;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StorageProvider {

    private final Settings settings;
    private final Configuration config;

    public StorageProvider(final Settings settings){

        this.settings = settings;
        this.config = settings.getConfig();

    }

    public void addProductToInventory(final Product product,final HInventory inventory){

        Pagination pagination = inventory.getPagination();

        if (product.hasItemStackContent()){

            ItemStack item = product.getItemStack();

            ClickableItem clickableItem = ClickableItem.of(item,click ->{

                AuctionPlayer player = click.getAuctionPlayer();
                player.addItemStackToInventory(item);
                removeProductFromInventory(product,inventory);
            });

            clickableItem.addValue("product",product);
            pagination.addItems(clickableItem);
        }

        if (product.hasMoneyContent()){

            AuctionPlayer buyer = product.getBuyer();
            double quantity = product.getMoney();

            ItemStack item = config.getMoneyItemStack(quantity,buyer);

            ClickableItem clickableItem = ClickableItem.of(item,click ->{

                AuctionPlayer player = click.getAuctionPlayer();
                player.depositMoney(quantity);
                removeProductFromInventory(product,inventory);
            });

            clickableItem.addValue("product",product);
            pagination.addItems(clickableItem);
        }
    }

    public void removeProductFromInventory(final Product product,final HInventory inventory){

        Pagination pagination = inventory.getPagination();

        List<ClickableItem> items = pagination.getClickableItems();

        for (int a=0; a<items.size(); a++){
            ClickableItem clickableItem = items.get(a);
            Product itemProduct = (Product) clickableItem.getValue("product");
            if (product.equals(itemProduct)) pagination.removeItems(a,true);
            break;
        }
    }


    public Storage getStorageFromByte(final byte[] bytes){
        return null;
    }

    public byte[] converStorageToBytes(final Storage storage){
        return new byte[31];
    }

}
