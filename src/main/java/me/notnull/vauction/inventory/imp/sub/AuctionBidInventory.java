package me.notnull.vauction.inventory.imp.sub;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.hakan.inventoryapi.inventory.HInventory;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.auction.AuctionManager;
import me.notnull.vauction.config.ConfigManager;
import me.notnull.vauction.config.Configuration;
import me.notnull.vauction.config.Settings;
import me.notnull.vauction.config.utils.PlayerMessage;
import me.notnull.vauction.inventory.InventoryProvider;
import me.notnull.vauction.inventory.utils.ItemBuilder;
import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AuctionBidInventory implements InventoryProvider {

    private final HInventory inventory;

    private final Configuration config;
    private final ConfigManager manager;

    public AuctionBidInventory(final Settings settings, final HInventory inventory){

        this.config = settings.getConfig();
        this.manager = config.getManager();
        this.inventory = inventory;
    }


    @Override
    public void init(Object object) {

        Auction auction = (Auction) object;
        auction.addBidInventory(inventory);

        inventory.setValue("auction",auction);
        inventory.setValue("offer",0);

        String path = "sub-inventories.auction-bid.items";

        ClickableItem confirm = manager.getClickableItem(path + ".confirm","menu.yml");
        ClickableItem cancel = manager.getClickableItem(path + ".cancel","menu.yml");

        inventory.setItem((int) confirm.getValue("slot"),confirm);
        inventory.setItem((int) cancel.getValue("slot"),cancel);

        confirm.setClick(click -> this.confirm(click.getAuctionPlayer()));
        cancel.setClick(click -> this.cancel(click.getAuctionPlayer()));

        Set<String> moneyItemStackList = manager.getKeys(path,"menu.yml",false);
        Arrays.asList("auction","confirm","cancel","fill").forEach(moneyItemStackList::remove);

        moneyItemStackList.forEach(key -> this.add(manager.getClickableItem(path + "." + key,"menu.yml")));
        this.update(auction);

        ItemStack fillItemStack = manager.getItemStack(path + ".fill","menu.yml");
        inventory.guiFill(fillItemStack);
    }

    @Override
    public void update(Object object) {

        int auctionItemSlot = manager.getInt("sub-inventories.auction-bid.items.auction.slot","menu.yml");

        int offer = (int) inventory.getValue("offer");
        Auction auction = (Auction) inventory.getValue("auction");

        List<String> lore = config.getAuctionBidOfferLore(offer,auction);
        ClickableItem item = new ItemBuilder(auction.getItemStack().clone()).setLore(lore).toClickableItem(click ->{});
        inventory.setItem(auctionItemSlot, item);
    }

    @Override
    public void add(Object... object) {

        ClickableItem clickableItem = (ClickableItem) object[0];
        int money = (int) clickableItem.getValue("money");

        int slot = (int) clickableItem.getValue("slot");

        clickableItem.setClick(hInventoryClickEvent ->{

            int offer = (int) inventory.getValue("offer") + money;
            if (offer < 0) offer = 0;

            inventory.setValue("offer",offer);
            this.update(null);
        });

        inventory.setItem(slot,clickableItem);
    }

    @Override
    public void cancel(Object object) {

        Auction auction = (Auction) inventory.getValue("auction");
        auction.removeBidInventory(inventory);

        AuctionPlayer player = (AuctionPlayer) object;
        player.closeInventory(inventory);
    }

    @Override
    public void confirm(Object object) {

        AuctionPlayer player = (AuctionPlayer) object;
        Auction auction = (Auction) inventory.getValue("auction");
        auction.removeBidInventory(inventory);

        int offer = (int) inventory.getValue("offer");
        double bid = Math.max(auction.getStartingPrice(), auction.getCurrentBid());

        if (!player.hasBalance(offer)){

            player.closeInventory(inventory);
            player.sendMessage(PlayerMessage.INSUFFICIENT_BALANCE);
            return;
        }

        if (bid >= offer){

            player.closeInventory(inventory);
            player.sendMessage(PlayerMessage.OFFER_MUST_BIGGER_THAN_CURRENT_PRICE);
            return;
        }

        if (auction.hasBuyer()){

            AuctionPlayer buyer = auction.getBuyer();
            buyer.sendMessage(PlayerMessage.SOMEONE_BID_HIGHER_THAN_YOU);
        }

        AuctionManager manager = auction.getManager();
        manager.bidAuction(auction,player,offer);
        player.sendMessage(PlayerMessage.BID_SUCCESS);
        player.closeInventory(inventory);
    }
}
