package me.notnull.vauction;

import com.hakan.inventoryapi.InventoryAPI;
import com.hakan.inventoryapi.InventoryCreator;
import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.AuctionManager;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.config.imp.AuctionConfiguration;
import me.notnull.vauction.config.imp.AuctionSettings;
import me.notnull.vauction.config.utils.SystemMessage;
import me.notnull.vauction.database.DatabaseManager;
import me.notnull.vauction.database.listener.DatabaseListener;
import me.notnull.vauction.config.ConfigManager;
import me.notnull.vauction.inventory.InventoryManager;
import me.notnull.vauction.inventory.utils.InventoryType;
import me.notnull.vauction.storage.StorageProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionServer {

    private final JavaPlugin plugin;

    private InventoryManager inventoryManager;
    private AuctionManager auctionManager;
    private DatabaseManager databaseManager;

    private ConfigManager configManager;
    private StorageProvider storageProvider;

    private Economy vaultEconomy;

    public AuctionServer(final JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void enable(){

        this.configManager = new ConfigManager(plugin);
        configManager.loadConfigurationFiles();

        final Configuration config = new AuctionConfiguration(configManager);
        final Settings settings = new AuctionSettings(config);

        this.storageProvider = new StorageProvider(settings);
        this.databaseManager = new DatabaseManager(storageProvider,inventoryManager);

        InventoryCreator creator = InventoryAPI.getInstance(plugin,databaseManager).getInventoryCreator();
        this.inventoryManager = new InventoryManager(creator,settings);

        HInventory available = inventoryManager.getInventory(InventoryType.AVAILABLE_AUCTIONS);
        HInventory expired = inventoryManager.getInventory(InventoryType.EXPIRED_AUCTIONS);
        this.auctionManager = new AuctionManager(available,expired);

        this.setupVaultProvider();

        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new DatabaseListener(databaseManager,vaultEconomy),plugin);

        plugin.getCommand("auction").setExecutor(new CommandListener(configManager,auctionManager,inventoryManager,databaseManager));
        auctionManager.runTimer(plugin);
    }

    public void disable(){

    }


    public void setupVaultProvider(){

        PluginManager manager = plugin.getServer().getPluginManager();
        ServicesManager service = plugin.getServer().getServicesManager();

        if (manager.getPlugin("Vault") == null) {
            plugin.getLogger().warning(SystemMessage.VAULT_PLUGIN_NOT_FOUND.getMessage());
            this.disable();
            return;
        }

        RegisteredServiceProvider<Economy> provider = service.getRegistration(Economy.class);

        if (provider == null) {
            plugin.getLogger().warning(SystemMessage.ECONOMY_PROVIDER_NOT_FOUND.getMessage());
            this.disable();
            return;
        }

        this.vaultEconomy = provider.getProvider();
        plugin.getLogger().info(SystemMessage.VAULT_SETUP_SUCCESS.getMessage());
    }








}
