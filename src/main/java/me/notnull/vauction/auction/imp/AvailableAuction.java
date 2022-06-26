package me.notnull.vauction.auction.imp;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.AuctionManager;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AvailableAuction implements Auction {

    private final String id;

    private final AuctionPlayer seller;
    private AuctionPlayer buyer;

    private final double startingPrice;
    private double currentPrice;

    private final ItemStack itemStack;
    private AuctionStatus auctionStatus;

    private int uptime;
    private int bidCooldown;

    private final List<HInventory> bidInventories;
    private final AuctionManager manager;

    public AvailableAuction(final String id, final AuctionPlayer seller, final double startingPrice, final ItemStack itemStack, final AuctionManager manager){

        this.id = id;
        this.seller = seller;
        this.startingPrice = startingPrice;
        this.itemStack = itemStack;
        this.auctionStatus = AuctionStatus.NOT_STARTED;
        this.manager = manager;

        this.currentPrice = 0;
        this.buyer = null;
        this.uptime = -1;
        this.bidCooldown = -1;
        this.bidInventories = new ArrayList<>();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public AuctionPlayer getSeller() {
        return seller;
    }

    @Override
    public AuctionPlayer getLastBidder() {
        return buyer;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    @Override
    public double getStartingPrice() {
        return startingPrice;
    }

    @Override
    public double getCurrentBid() {
        return currentPrice;
    }

    @Override
    public AuctionManager getManager() {
        return manager;
    }

    @Override
    public AuctionStatus getStatus() {
        return auctionStatus;
    }

    @Override
    public void setStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    @Override
    public void updateUptime() {

        if (auctionStatus.equals(AuctionStatus.BID_COOLDOWN)){
            bidCooldown-=1;
            if (bidCooldown <= 0) setStatus(AuctionStatus.AVAILABLE);
        }

        uptime-=1;
        if (uptime <= 10) setStatus(AuctionStatus.ALMOST_FINISH);
        if (uptime <= 0) manager.finishAuction(this);
    }

    @Override
    public int getUptime() {
        return uptime;
    }

    @Override
    public void setUptime(int uptime){
        this.uptime = uptime;
    }

    @Override
    public int getBidCooldown() {
        return bidCooldown;
    }

    @Override
    public void setBidCooldown(int bidCooldown) {
        this.bidCooldown = bidCooldown;
    }

    @Override
    public void setCurrentPrice(double currentPrice){
        this.currentPrice = currentPrice;
    }

    @Override
    public void setBuyer(AuctionPlayer buyer){
        this.buyer = buyer;
    }

    @Override
    public AuctionPlayer getBuyer(){return this.buyer;}

    @Override
    public boolean hasBuyer(){return buyer != null;}

    @Override
    public void removeBidInventory(HInventory inventory) {
        this.bidInventories.remove(inventory);
    }

    @Override
    public void addBidInventory(HInventory inventory) {
        this.bidInventories.add(inventory);
    }

    @Override
    public List<HInventory> getBidInventories() {
        return bidInventories;
    }
}
