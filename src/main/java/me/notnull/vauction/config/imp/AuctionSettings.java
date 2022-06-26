package me.notnull.vauction.config.imp;

import me.notnull.vauction.config.ConfigManager;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;

public class AuctionSettings implements Settings {

    private final Configuration configuration;
    private final ConfigManager manager;

    public AuctionSettings(final Configuration configuration){
        this.configuration = configuration;
        this.manager = configuration.getManager();
    }

    @Override
    public Configuration getConfig() {
        return configuration;
    }

    @Override
    public int getUptime() {
        return manager.getInt("auction-settings.uptime","settings.yml");
    }

    @Override
    public int getCooldown() {
        return manager.getInt("auction-settings.cooldown","settings.yml");
    }

    @Override
    public double getMaxStartingPrice() {
        return manager.getDouble("auction-settings.max-starting-price","settings.yml");
    }

    @Override
    public double getMinStartingPrice() {
        return manager.getDouble("auction-settings.min-starting-price","settings.yml");
    }

    @Override
    public String getInvalidBuyer() {
        return manager.getString("auction-settings.invalid-buyer",false,"settings.yml");
    }

    @Override
    public String getInvalidCurrentBid() {
        return manager.getString("auction-settings.invalid-current-bid",false,"settings.yml");
    }
}
