package me.notnull.vauction.inventory.imp;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.inventory.Pagination;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.config.ConfigManager;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.inventory.InventoryManager;
import me.notnull.vauction.inventory.InventoryProvider;
import me.notnull.vauction.inventory.utils.InventoryType;

import java.util.List;

public class HistoryInventory implements InventoryProvider {

    private final HInventory inventory;
    private final Configuration config;

    public HistoryInventory(final Settings settings, final InventoryManager manager){
        this.config = settings.getConfig();
        this.inventory = manager.getInventory(InventoryType.EXPIRED_AUCTIONS);
    }

    @Override
    public void init(Object object) {

        Pagination pagination = inventory.getPagination();
        pagination.setItemSlots(0,53);

        ConfigManager manager = config.getManager();

        ClickableItem nextPage = manager.getClickableItem("inventories.expired-auctions.items.next-page","menu.yml");
        ClickableItem previousPage = manager.getClickableItem("inventories.expired-auctions.items.previous-page","menu.yml");

        nextPage.setClick(click -> pagination.nextPage());
        previousPage.setClick(click -> pagination.previousPage());

        inventory.setItem((int)nextPage.getValue("slot"),nextPage);
        inventory.setItem((int)previousPage.getValue("slot"),previousPage);
    }

    @Override
    public void add(Object... object) {

        Auction auction = (Auction) object[0];
        ClickableItem item = config.getAuctionItemStack(auction).toClickableItem(click ->{});
        inventory.getPagination().addItems(item);
    }

    @Override
    public void remove(Object object) {

        Auction auction = (Auction) object;

        Pagination pag = inventory.getPagination();
        List<ClickableItem> items = pag.getClickableItems();

        for (int a=0; a<items.size(); a++){

            Auction itemAuction = (Auction) items.get(a).getValue("auction");
            if (auction.getID().equals(itemAuction.getID())){
                pag.removeItems(a,true);
                break;
            }
        }
    }

    @Override
    public void update(Object object) {}
}
