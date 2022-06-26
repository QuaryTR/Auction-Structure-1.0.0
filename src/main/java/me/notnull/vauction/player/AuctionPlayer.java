package me.notnull.vauction.player;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.config.utils.PlayerMessage;
import me.notnull.vauction.storage.Storage;
import me.notnull.vauction.player.util.Preferences;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface AuctionPlayer {

    Preferences getPreferences();

    String getName();

    Storage getStorage();

    void setStorage(final Storage storage);

    void addItemStackToInventory(final ItemStack itemStack);

    List<Auction> getExpiredAuctions();

    List<Auction> getAvailableAuctions();

    void addAvailableAuction(final Auction auction);

    void removeAvailableAuction(final Auction auction);

    void addExpiredAuction(final Auction auction);

    void removeExpiredAuction(final Auction auction);

    boolean hasInventorySpace(final ItemStack itemStack);

    UUID getPlayerUUID();

    double getBalance();

    void depositMoney(final double quantity);

    void withdrawMoney(final double quantity);

    Player getOnlinePlayer();

    OfflinePlayer getOfflinePlayer();

    boolean isOnline();

    void sendMessage(final PlayerMessage message);

    void sendMessage(final String message);

    void closeInventory(final HInventory inventory);

    void openInventory(final HInventory inventory);

    boolean hasBalance(final double quantity);

}
