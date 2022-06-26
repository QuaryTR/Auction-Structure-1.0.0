package me.notnull.vauction.config;

public interface Settings {

    Configuration getConfig();

    int getUptime();

    int getCooldown();

    double getMaxStartingPrice();

    double getMinStartingPrice();

    String getInvalidBuyer();

    String getInvalidCurrentBid();
}
