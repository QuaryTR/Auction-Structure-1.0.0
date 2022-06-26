package me.notnull.vauction.database.listener;

import me.notnull.vauction.database.DatabaseManager;
import me.notnull.vauction.storage.StorageProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class DatabaseListener implements Listener {

    private final DatabaseManager manager;
    private final Economy economy;

    public DatabaseListener(final DatabaseManager manager, final Economy economy){
        this.manager = manager;
        this.economy = economy;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event){

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (manager.hasAuctionPlayerData(playerUUID)) manager.loadAuctionPlayerData(playerUUID);
        else manager.createAuctionPlayerData(playerUUID,economy);
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event){

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        //TODO BUKKIT ASYNC TASK

        if (manager.hasAuctionPlayerData(playerUUID)) manager.saveAuctionPlayerData(playerUUID);
    }


}
