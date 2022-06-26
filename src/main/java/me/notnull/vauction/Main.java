package me.notnull.vauction;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static AuctionServer server;

    @Override
    public void onEnable() {

        server = new AuctionServer(this);
        server.enable();
    }

    @Override
    public void onDisable() {

        server.disable();
        server = null;
    }

    public static AuctionServer getAuctionServer(){
        return server;
    }


}
