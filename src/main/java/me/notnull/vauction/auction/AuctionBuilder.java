package me.notnull.vauction.auction;

import me.notnull.vauction.auction.imp.AvailableAuction;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.inventory.ItemStack;

public class AuctionBuilder {

    private final String auctionId;
    private double startingPrice;

    private ItemStack itemStack;
    private AuctionPlayer seller;

    private AuctionManager manager;

    public AuctionBuilder(final String auctionId){
        this.auctionId = auctionId;
    }

    public AuctionBuilder seller(final AuctionPlayer seller){
        this.seller = seller;
        return this;
    }

    public AuctionBuilder itemStack(final ItemStack itemStack){
        this.itemStack = itemStack;
        return this;
    }

    public AuctionBuilder price(final double startingPrice){
        this.startingPrice = startingPrice;
        return this;
    }

    public AuctionBuilder manager(final AuctionManager manager){
        this.manager = manager;
        return this;
    }

    public Auction create(){
        return new AvailableAuction(auctionId,seller,startingPrice,itemStack,manager);
    }





}
