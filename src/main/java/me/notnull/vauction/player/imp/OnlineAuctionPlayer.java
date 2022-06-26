package me.notnull.vauction.player.imp;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.config.utils.PlayerMessage;
import me.notnull.vauction.storage.Storage;
import me.notnull.vauction.player.AuctionPlayer;
import me.notnull.vauction.player.util.Preferences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OnlineAuctionPlayer implements AuctionPlayer {

    private final UUID playerUUID;

    private final List<Auction> availableAuctions;
    private final List<Auction> expiredAuctions;

    private final Economy economy;
    private Storage storage;

    public OnlineAuctionPlayer(final UUID playerUUID, final Economy economy){

        this.playerUUID = playerUUID;
        this.economy = economy;

        this.availableAuctions = new ArrayList<>();
        this.expiredAuctions = new ArrayList<>();
        this.storage = null;
    }

    @Override
    public Preferences getPreferences() {
        return new Preferences();
    }

    @Override
    public String getName() {
        return getOnlinePlayer().getName();
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public void setStorage(Storage storage) {this.storage = storage;}

    @Override
    public void addItemStackToInventory(ItemStack itemStack) {

    }

    @Override
    public List<Auction> getExpiredAuctions() {
        return expiredAuctions;
    }

    @Override
    public List<Auction> getAvailableAuctions() {
        return availableAuctions;
    }

    @Override
    public void addAvailableAuction(Auction auction) {
        availableAuctions.add(auction);
    }

    @Override
    public void removeAvailableAuction(Auction auction) {
        availableAuctions.remove(auction);
    }

    @Override
    public void addExpiredAuction(Auction auction) {
        expiredAuctions.add(auction);
    }

    @Override
    public void removeExpiredAuction(Auction auction) {
        expiredAuctions.remove(auction);
    }

    @Override
    public boolean hasInventorySpace(ItemStack itemStack) {
        return false;
    }

    @Override
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    @Override
    public double getBalance() {
        return economy.getBalance(getOnlinePlayer());
    }

    @Override
    public boolean hasBalance(double quantity) {
        return getBalance() >= quantity;
    }

    @Override
    public void depositMoney(double quantity) {
        economy.depositPlayer(getOfflinePlayer(),quantity);
    }

    @Override
    public void withdrawMoney(double quantity) {
        economy.withdrawPlayer(getOfflinePlayer(),quantity);
    }

    @Override
    public Player getOnlinePlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(playerUUID);
    }

    @Override
    public boolean isOnline() {
        return (getOnlinePlayer() != null && getOnlinePlayer().isOnline());
    }

    @Override
    public void sendMessage(PlayerMessage message) {
        getOnlinePlayer().sendMessage(message.getMessage());
    }

    @Override
    public void sendMessage(String message) {
        getOnlinePlayer().sendMessage(message);
    }

    @Override
    public void closeInventory(HInventory inventory) {
        inventory.close(getOnlinePlayer());
    }

    @Override
    public void openInventory(HInventory inventory) {
        inventory.open(getOnlinePlayer());
    }
}
