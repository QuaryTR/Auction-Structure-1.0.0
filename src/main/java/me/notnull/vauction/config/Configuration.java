package me.notnull.vauction.config;

import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.inventory.utils.InventoryType;
import me.notnull.vauction.inventory.utils.ItemBuilder;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Configuration {

    ConfigManager getManager();

    List<String> getAuctionStatusLore(final AuctionStatus status);

    List<String> getAuctionBidOfferLore(final double offer,final Auction auction);

    ItemBuilder getAuctionItemStack(final Auction auction);

    ItemStack getMoneyItemStack(final double quantity, final AuctionPlayer player);

    String getInventoryTitle(final InventoryType type);

    ItemStack getStorageItemStack(final ItemStack auctionItem);

    int getInventorySize(final InventoryType type);
}
