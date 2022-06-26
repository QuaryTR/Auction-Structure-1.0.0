package me.notnull.vauction.config.imp;

import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.config.ConfigManager;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.inventory.utils.InventoryType;
import me.notnull.vauction.inventory.utils.ItemBuilder;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;

public class AuctionConfiguration implements Configuration {

    private final ConfigManager manager;

    public AuctionConfiguration(final ConfigManager manager){
        this.manager = manager;
    }

    @Override
    public ConfigManager getManager() {
        return manager;
    }

    @Override
    public List<String> getAuctionStatusLore(AuctionStatus status) {
        String statusString = status.toString().toLowerCase(Locale.ENGLISH).replace("_","-");
        return manager.getStringList("utils.lores." + statusString,"menu.yml",true);
    }

    @Override
    public List<String> getAuctionBidOfferLore(double offer, Auction auction) {
        return manager.getBidItemLore(offer,auction);
    }

    @Override
    public ItemBuilder getAuctionItemStack(Auction auction) {

        ItemStack auctionItem = auction.getItemStack().clone();
        return new ItemBuilder(auctionItem).setLore(manager.getAuctionItemStackLore(auction));
    }

    @Override
    public ItemStack getMoneyItemStack(double quantity, AuctionPlayer player) {
        return null;
    }

    @Override
    public String getInventoryTitle(InventoryType type) {

        String inventoryString = "inventories." + type.toString().toLowerCase(Locale.ENGLISH).replace("_","-");
        if (type.equals(InventoryType.AUCTION_BID) || type.equals(InventoryType.AUCTION_CANCEL)) inventoryString = "sub-" + inventoryString;
        return manager.getString(inventoryString + ".title",true,"menu.yml");
    }

    @Override
    public ItemStack getStorageItemStack(ItemStack auctionItem) {
        return null;
    }

    @Override
    public int getInventorySize(InventoryType type) {

        String inventoryString = "inventories." + type.toString().toLowerCase(Locale.ENGLISH).replace("_","-");
        if (type.equals(InventoryType.AUCTION_BID) || type.equals(InventoryType.AUCTION_CANCEL)) inventoryString = "sub-" + inventoryString;
        return manager.getInt(inventoryString + ".size","menu.yml");
    }
}
