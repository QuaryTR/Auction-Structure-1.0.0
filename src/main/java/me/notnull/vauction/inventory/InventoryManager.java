package me.notnull.vauction.inventory;

import com.hakan.inventoryapi.InventoryCreator;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.inventory.Pagination;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.inventory.imp.AuctionsInventory;
import me.notnull.vauction.inventory.imp.HistoryInventory;
import me.notnull.vauction.inventory.imp.sub.AuctionBidInventory;
import me.notnull.vauction.inventory.utils.InventoryType;
import me.notnull.vauction.player.AuctionPlayer;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private final Map<InventoryType, HInventory> inventories;

    private final InventoryCreator creator;

    private final Settings settings;
    private final Configuration config;

    public InventoryManager(final InventoryCreator creator,final Settings settings){

        this.settings = settings;
        this.creator = creator;
        this.config = settings.getConfig();

        this.inventories = new HashMap<>();

        createMainInventory();
        createAvailableAuctionsInventory();
        createExpiredAuctionsInventory();
        createStorageInventoryStructure();
    }

    private void createMainInventory(){

        //int size = config.getInt("inventories.main-inventory.size","menu.yml");
        //String title = config.getString("inventories.main-inventory.title",true,"menu.yml");
        //HInventory inventory = creator.setSize(size).setTitle(title).setClickable(false).create();

    }

    private void createAvailableAuctionsInventory(){

        String title = config.getInventoryTitle(InventoryType.AVAILABLE_AUCTIONS);
        HInventory inventory = creator.setSize(6).setTitle(title).setClickable(false).create();

        InventoryProvider provider = new AuctionsInventory(settings,this,inventory);
        inventory.setProvider(provider);
        provider.init(null);

        inventories.put(InventoryType.AVAILABLE_AUCTIONS,inventory);
    }

    private void createExpiredAuctionsInventory(){

        String title = config.getInventoryTitle(InventoryType.EXPIRED_AUCTIONS);
        HInventory inventory = creator.setSize(6).setTitle(title).setClickable(false).create();
        inventory.setProvider(new HistoryInventory(settings,this));
        inventories.put(InventoryType.EXPIRED_AUCTIONS,inventory);
    }

    private void createStorageInventoryStructure(){

        String title = config.getInventoryTitle(InventoryType.PLAYER_STORAGE);
        HInventory inventory = creator.setSize(6).setTitle(title).setClickable(false).create();

        Pagination pagination = inventory.getPagination();
        pagination.setItemSlots(0,53);

        inventories.put(InventoryType.PLAYER_STORAGE,inventory);
    }

    public HInventory getInventory(final InventoryType type){

        HInventory inventory = inventories.get(type);
        if (type.equals(InventoryType.PLAYER_STORAGE)) inventory = inventories.get(type).clone();
        return inventory;
    }

    public void openInventory(final InventoryType type,final AuctionPlayer player){

        HInventory inventory = inventories.get(type);
        player.openInventory(inventory);
    }


    public void openBidInventory(final Auction auction,final AuctionPlayer player){

        String title = config.getInventoryTitle(InventoryType.AUCTION_BID);
        int size = config.getInventorySize(InventoryType.AUCTION_BID);

        HInventory inventory = creator.setTitle(title).setSize(size).setClickable(false).create();
        InventoryProvider provider = new AuctionBidInventory(settings,inventory);
        inventory.setProvider(provider);

        provider.init(auction);
        player.openInventory(inventory);
    }


    public void openWaitingAuctionsInventory(final AuctionPlayer player){

    }







}
