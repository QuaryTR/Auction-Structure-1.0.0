package me.notnull.vauction.config;

import com.hakan.inventoryapi.inventory.ClickableItem;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.utils.AuctionStatus;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ConfigManager {

    private final Map<String, YamlConfiguration> configurationFiles = new HashMap<>();
    private final Plugin plugin;

    public ConfigManager(final Plugin plugin){
        this.plugin = plugin;
    }

    public void loadConfigurationFiles(){

        String[] list = {"menu.yml", "settings.yml", "language.yml"};

        plugin.getDataFolder().mkdirs();

        for (String string : list){

            File file = new File(plugin.getDataFolder(), string);

            if (!file.exists()){
                plugin.saveResource(string, false);
            }

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configurationFiles.put(string,configuration);
        }

        plugin.getLogger().info("All config files has been loaded.");
    }

    public String getString(final String path,final boolean colored,final String configFile){

        String string = "null";

        if (configurationFiles.containsKey(configFile)){

            YamlConfiguration config = configurationFiles.get(configFile);
            string = config.getString(path);

            if (colored){
                string = ChatColor.translateAlternateColorCodes('&',string);
            }
        }

        return string;
    }

    public double getDouble(final String path ,final String configFile){

        double doubleValue = 0.0;

        if (configurationFiles.containsKey(configFile)){

            YamlConfiguration config = configurationFiles.get(configFile);
            doubleValue = config.getDouble(path);
        }

        return doubleValue;
    }

    public List<String> getStringList(final String path, final String configFile, final boolean colored){

        List<String> list = new ArrayList<>();

        if (configurationFiles.containsKey(configFile)){

            YamlConfiguration config = configurationFiles.get(configFile);
            list = config.getStringList(path);

            if (colored) {
                for (int a = 0; a < list.size(); a++) {
                    String string = ChatColor.translateAlternateColorCodes('&', list.get(a));
                    list.set(a, string);
                }
            }
        }
        return list;
    }

    public boolean getBoolean(final String path,final String configFile){

        boolean bool = false;

        if (configurationFiles.containsKey(configFile)){

            YamlConfiguration config = configurationFiles.get(configFile);
            bool = config.getBoolean(path);
        }

        return bool;
    }

    public int getInt(final String path ,final String configFile){

        int value = 0;

        if (configurationFiles.containsKey(configFile)){

            YamlConfiguration config = configurationFiles.get(configFile);
            value = config.getInt(path);
        }

        return value;
    }

    public Set<String> getKeys(final String path, final String configFile, boolean keys){

        Set<String> keySet = new HashSet<>();

        if (configurationFiles.containsKey(configFile)){

            YamlConfiguration config = configurationFiles.get(configFile);
            keySet = config.getConfigurationSection(path).getKeys(keys);
        }

        return keySet;
    }

    public ItemStack getItemStack(final String path, final String configFile){

        ItemStack itemStack = new ItemStack(Material.AIR);

        if (configurationFiles.containsKey(configFile)){

            String materialString = getString(path + ".material",false,configFile);
            Material material = Material.valueOf(materialString);

            int data = getInt(path + ".data",configFile);

            itemStack = new ItemStack(material,1,(short)data);

            List<String> lore = getStringList(path + ".lore",configFile,true);
            String name = getString(path + ".name", true,configFile);

            ItemMeta meta = itemStack.getItemMeta();

            if (meta != null){
                meta.setLore(lore);
                meta.setDisplayName(name);
            }

            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    public String getPlayerName(final AuctionPlayer player){

        String name = getString("auction-settings.invalid-buyer",true,"settings.yml");

        if (player != null) {
            if (player.isOnline()) name = player.getOnlinePlayer().getName();
            else name = player.getOfflinePlayer().getName();
        }

        return name;
    }

    public String getUptimeString(final int uptime){

        int b = uptime % 60;
        int a = uptime / 60;

        String uptimeString = getString("auction-settings.uptime-string",true,"settings.yml");
        return uptimeString.replace("%minute%",String.valueOf(a)).replace("%second%",String.valueOf(b));
    }


    public List<String> getAuctionItemStackLore(final Auction auction){

        AuctionStatus status = auction.getStatus();
        String statusString = status.toString().toLowerCase(Locale.ENGLISH).replace("_","-");

        List<String> lore = getStringList("utils.lores." + statusString,"menu.yml",true);

        LocalDate date = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        String dateStr = date.getDayOfMonth() + " / " + date.getMonthValue() + " / " + date.getYear();
        String timeStr = localTime.getMinute() + " / " + localTime.getHour();

        for (int a=0; a<lore.size(); a++){

            lore.set(a,lore.get(a)
                    .replace("%auction-id%",auction.getID())
                    .replace("%seller%", getPlayerName(auction.getSeller()))
                    .replace("%starting-bid%",String.valueOf(auction.getStartingPrice()))
                    .replace("%buyer%",getPlayerName(auction.getBuyer()))
                    .replace("%current-bid%",String.valueOf(auction.getCurrentBid()))
                    .replace("%uptime%",getUptimeString(auction.getUptime()))
                    .replace("%bid-uptime%",String.valueOf(auction.getBidCooldown()))
                    .replace("%date%",dateStr)
                    .replace("%time%",timeStr));

        }

        return lore;
    }

    public List<String> getBidItemLore(final double offer,final Auction auction){

        List<String> lore = getStringList("utils.lores.bid","menu.yml",true);

        String buyerString = getString("auction-settings.invalid-buyer",true,"settings.yml");
        String priceString = getString("auction-settings.invalid-current-price",true,"settings.yml");

        if (auction.hasBuyer()){
            buyerString = auction.getBuyer().getName();
            priceString = String.valueOf(auction.getCurrentBid());
        }

        for (int a=0; a<lore.size(); a++){

            lore.set(a,lore.get(a)
                    .replace("%auction-id%",auction.getID())
                    .replace("%seller%", auction.getSeller().getName())
                    .replace("%starting-bid%",String.valueOf(auction.getStartingPrice()))
                    .replace("%buyer%",buyerString)
                    .replace("%current-bid%",priceString)
                    .replace("%offer%",String.valueOf(offer)));
        }

        return lore;
    }

    public ClickableItem getClickableItem(final String path,final String configFile){

            ItemStack itemStack = getItemStack(path,configFile);
            int slot = getInt(path + ".slot",configFile);

            ClickableItem clickableItem = ClickableItem.empty(itemStack);
            clickableItem.addValue("slot",slot);

            Set<String> keys = getKeys(path,configFile,false);
            Arrays.asList("slot","name","material","data","lore","glow").forEach(keys::remove);

            keys.forEach(key -> clickableItem.addValue(key,configurationFiles.get(configFile).get(path + "." + key)));

        return clickableItem;
    }




}
