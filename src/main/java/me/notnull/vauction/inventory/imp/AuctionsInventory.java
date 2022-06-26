package me.notnull.vauction.inventory.imp;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.inventory.Pagination;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.config.utils.PlayerMessage;
import me.notnull.vauction.inventory.InventoryManager;
import me.notnull.vauction.inventory.InventoryProvider;
import me.notnull.vauction.player.AuctionPlayer;

import java.util.List;

public class AuctionsInventory implements InventoryProvider {

    private final Settings settings;
    private final InventoryManager manager;
    private final HInventory inventory;

    public AuctionsInventory(final Settings settings, final InventoryManager manager,final HInventory inventory){
        this.settings = settings;
        this.manager  = manager;
        this.inventory = inventory;
    }

    @Override
    public void init(Object object) {

        Pagination pagination = inventory.getPagination();
        pagination.setItemSlots(0,53);
    }

    @Override
    public void update(Object object) {

        Configuration config = settings.getConfig();

        Pagination pag = inventory.getPagination();
        List<ClickableItem> items = pag.getClickableItems();

        if (items.isEmpty()) return;

        int lenght = items.size();
        if (lenght >54) lenght=54;

        for (int slot=0; slot<lenght; slot++){

            ClickableItem clickableItem = items.get(slot);
            Auction auction = (Auction) clickableItem.getValue("auction");

            auction.updateUptime();
            clickableItem.setItem(config.getAuctionItemStack(auction).toItemStack());
            inventory.setItem(slot,clickableItem);
        }
    }

    @Override
    public void add(Object... object) {

        final Auction auction = (Auction) object[0];
        Configuration config = settings.getConfig();

        ClickableItem clickableItem = config.getAuctionItemStack(auction).toClickableItem(click ->{

        AuctionPlayer player = click.getAuctionPlayer();
        AuctionStatus status = auction.getStatus();

        if (!status.equals(AuctionStatus.AVAILABLE)){

            player.closeInventory(inventory);
            player.sendMessage(PlayerMessage.valueOf(status.name()));

        }else manager.openBidInventory(auction,player);

        });

        clickableItem.addValue("auction",auction);
        inventory.getPagination().addItems(clickableItem);
    }

    @Override
    public void remove(Object object) {

        Auction auction = (Auction) object;

        int slot = -1;

        Pagination pag = inventory.getPagination();
        List<ClickableItem> items = pag.getClickableItems();

        for (int a=0; a<items.size(); a++){

            ClickableItem item = items.get(a);
            Auction itemAuc = (Auction) item.getValue("auction");

            if (auction.getID().equals(itemAuc.getID())){
                slot = a;
                break;
            }
        }

        if (slot != -1) pag.removeItems(slot,true);
    }
}
