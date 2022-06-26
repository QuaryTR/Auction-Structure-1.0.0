package me.notnull.vauction.auction;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface Auction {


    /**
     * @return ID of the auction.
     **/

    String getID();

    /**
     * @return owner of the auction.
     **/

    AuctionPlayer getSeller();

    /**
     * @return customer of the auction.
     **/

    AuctionPlayer getLastBidder();

    /**
     * @return ItemStack selling at auction.
     **/

    ItemStack getItemStack();

    /**
     * @return Starting price of the auction.
     **/








    double getStartingPrice();

    /**
     * @return Current bid of the auction.
     * if #customer is null current bid will be zero.
     **/

    double getCurrentBid();

    /**
     *
     * @return
     */

    AuctionManager getManager();




    // AVAILABLE AUCTION

    default AuctionStatus getStatus(){return null;}

    default void setStatus(AuctionStatus auctionStatus){}

    default void updateUptime(){}

    /**
     * @return Time until the end of the auction.
     */
    default int getUptime() {return 0;}

    default void setUptime(final int uptime){}

    /**
     * When AuctionBidEvent automaticly set it to 10
     * @return Time until the end of bid cooldown.
     */
    default int getBidCooldown(){return 0;}

    default void setBidCooldown(final int cooldown){}

    default void setCurrentPrice(final double currentPrice){}

    default void setBuyer(final AuctionPlayer buyer){}

    default AuctionPlayer getBuyer(){return null;}

    default boolean hasBuyer(){return false;}

    default void addBidInventory(final HInventory inventory){}

    default void removeBidInventory(final HInventory inventory){}

    default List<HInventory> getBidInventories(){return new ArrayList<>();}





}
