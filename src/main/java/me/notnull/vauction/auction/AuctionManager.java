package me.notnull.vauction.auction;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.event.AuctionCancelEvent;
import me.notnull.vauction.auction.event.AuctionFinishEvent;
import me.notnull.vauction.auction.event.AuctionStartEvent;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.storage.Storage;
import me.notnull.vauction.inventory.InventoryProvider;
import me.notnull.vauction.player.AuctionPlayer;
import me.notnull.vauction.storage.utils.Product;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuctionManager {

    private final HInventory availableAuctionsInventory;
    private final HInventory expiredAuctionsInventory;

    private final List<Auction> availableAuctions;
    private final List<Auction> waitingAuctions;

    public AuctionManager(final HInventory available,final HInventory expired){

        this.availableAuctions = new ArrayList<>();
        this.waitingAuctions = new ArrayList<>();

        this.availableAuctionsInventory = available;
        this.expiredAuctionsInventory = expired;
    }

    public void startAuction(final Auction auction){

        Event event = new AuctionStartEvent(auction);
        Bukkit.getPluginManager().callEvent(event);

        AuctionStartEvent startEvent = (AuctionStartEvent) event;
        if (startEvent.isCancelled()) return;

        AuctionPlayer owner = auction.getSeller();
        owner.addAvailableAuction(auction);

        availableAuctionsInventory.getProvider().add(auction);

        if (availableAuctions.size() >= 54) waitingAuctions.add(auction);
        else {availableAuctions.add(auction); auction.setStatus(AuctionStatus.AVAILABLE);}
    }

    public void startNextAuction(){

        if (waitingAuctions.isEmpty()) return;
        Auction auction = waitingAuctions.get(0);
        this.startAuction(auction);
        waitingAuctions.remove(0);
    }

    public void cancelAuction(final Auction auction){

        Event event = new AuctionCancelEvent(auction);
        Bukkit.getPluginManager().callEvent(event);

        AuctionCancelEvent cancelEvent = (AuctionCancelEvent) event;
        if (cancelEvent.isCancelled()) return;

        availableAuctionsInventory.getProvider().add(auction);

        auction.setStatus(AuctionStatus.CANCELLED);
        AuctionPlayer owner = auction.getSeller();

        Storage storage = owner.getStorage();
        storage.addProduct(Product.of(auction.getItemStack()));

        owner.removeAvailableAuction(auction);
        this.waitingAuctions.remove(auction);
    }

    public void finishAuction(final Auction auction){

        Event event = new AuctionFinishEvent(auction);
        Bukkit.getPluginManager().callEvent(event);

        AuctionFinishEvent finishEvent = (AuctionFinishEvent) event;
        if (finishEvent.isCancelled()) return;

        auction.setStatus(AuctionStatus.EXPIRED);

        AuctionPlayer owner = auction.getSeller();

        availableAuctionsInventory.getProvider().remove(auction);
        expiredAuctionsInventory.getProvider().add(auction);

        if (auction.hasBuyer()){

            double money = auction.getCurrentBid();
            AuctionPlayer buyer = auction.getBuyer();

            Storage ownerStorage = owner.getStorage();
            ownerStorage.addProduct(Product.of(money,buyer));

            Storage buyerStorage = buyer.getStorage();
            buyerStorage.addProduct(Product.of(auction.getItemStack()));

        }else{
            Storage ownerStorage = owner.getStorage();
            ownerStorage.addProduct(Product.of(auction.getItemStack()));
        }

        owner.removeAvailableAuction(auction);
        this.waitingAuctions.remove(auction);
        startNextAuction();
    }

    public void bidAuction(final Auction auction, final AuctionPlayer buyer, final double price){

        auction.setStatus(AuctionStatus.BID_COOLDOWN);
        auction.setBidCooldown(10);

        auction.setBuyer(buyer);
        auction.setCurrentPrice(price);
        buyer.withdrawMoney(price);
    }

    public String generateAuctionId(){

        Random rand = new Random();
        int randomNum = rand.nextInt((999999999 - 100000000) + 1) + 100000000;
        return String.valueOf(randomNum);
    }

    public void runTimer(final JavaPlugin plugin){

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {

            InventoryProvider provider = availableAuctionsInventory.getProvider();
            provider.update(null);

        },0L,20L);
    }

}
