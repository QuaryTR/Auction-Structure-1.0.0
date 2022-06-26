package me.notnull.vauction.storage.utils;

import me.notnull.vauction.player.AuctionPlayer;
import org.bukkit.inventory.ItemStack;

public class Product {

    private final ItemStack itemStack;
    private final double money;
    private final AuctionPlayer player;

    public Product(final double money,final ItemStack itemStack,final AuctionPlayer player){
        this.itemStack = itemStack;
        this.money = money;
        this.player = player;
    }

    public static Product of(final ItemStack itemStack){
        return new Product(0,itemStack,null);
    }

    public static Product of(final double money,final AuctionPlayer player){
        return new Product(money,null,player);
    }


    public boolean hasItemStackContent(){
        return itemStack != null;
    }

    public boolean hasMoneyContent(){
        return money != 0;
    }

    public ItemStack getItemStack(){
        return itemStack;
    }

    public double getMoney(){
        return money;
    }

    public AuctionPlayer getBuyer(){
        return player;
    }



}
