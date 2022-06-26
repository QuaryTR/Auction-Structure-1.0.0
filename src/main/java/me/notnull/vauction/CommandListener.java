package me.notnull.vauction;

import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.AuctionBuilder;
import me.notnull.vauction.auction.AuctionManager;
import me.notnull.vauction.config.utils.PlayerMessage;
import me.notnull.vauction.database.DatabaseManager;
import me.notnull.vauction.config.ConfigManager;
import me.notnull.vauction.inventory.InventoryManager;
import me.notnull.vauction.inventory.utils.InventoryType;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandListener implements CommandExecutor {


    private final ConfigManager configManager;
    private final AuctionManager auctionManager;
    private final InventoryManager inventoryManager;
    private final DatabaseManager databaseManager;

    public CommandListener(final ConfigManager configManager,final AuctionManager auctionManager,final InventoryManager inventoryManager,final DatabaseManager databaseManager){
        this.inventoryManager = inventoryManager;
        this.auctionManager = auctionManager;
        this.configManager = configManager;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player p = (Player) commandSender;

        Player k = Bukkit.getPlayer("NotNull");
        if (k == null) Bukkit.broadcastMessage("NotNull null");
        else Bukkit.broadcastMessage("NotNull null değil");

        Player x = Bukkit.getPlayer("NotNull2");
        if (x == null) Bukkit.broadcastMessage("NotNull2 null");
        else Bukkit.broadcastMessage("NotNull2 null değil");

        Bukkit.broadcastMessage(String.valueOf(x.isOnline()));

        return false;
    }
}
