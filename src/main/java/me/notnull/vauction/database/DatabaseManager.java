package me.notnull.vauction.database;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.inventory.InventoryManager;
import me.notnull.vauction.inventory.utils.InventoryType;
import me.notnull.vauction.player.AuctionPlayer;
import me.notnull.vauction.player.imp.OnlineAuctionPlayer;
import me.notnull.vauction.storage.Storage;
import me.notnull.vauction.storage.StorageProvider;
import me.notnull.vauction.storage.imp.AuctionStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseManager {

    private final StorageProvider provider;
    private final InventoryManager inventoryManager;

    private final Map<UUID, AuctionPlayer> auctionPlayerData = new HashMap<>();

    public DatabaseManager(final StorageProvider provider,final InventoryManager inventoryManager){
        this.provider = provider;
        this.inventoryManager = inventoryManager;
    }

    public AuctionPlayer getAuctionPlayer(final Player player){
        return auctionPlayerData.get(player.getUniqueId());
    }

    public boolean hasAuctionPlayerData(final UUID playerUUID){
        return false;
    }

    public void createAuctionPlayerData(final UUID playerUUID,final Economy economy){

        AuctionPlayer player = new OnlineAuctionPlayer(playerUUID,economy);
        this.auctionPlayerData.put(playerUUID,player);

        Storage storage = new AuctionStorage(provider,player);
        HInventory inventory = inventoryManager.getInventory(InventoryType.PLAYER_STORAGE);
        storage.setInventory(inventory);

        player.setStorage(storage);
        Bukkit.broadcastMessage("AUCTION PLAYER DATA CREATED : " + playerUUID.toString());
    }


    public void loadAuctionPlayerData(final UUID playerUUID){

    }


    public void saveAuctionPlayerData(final UUID playerUUID){

    }



}
