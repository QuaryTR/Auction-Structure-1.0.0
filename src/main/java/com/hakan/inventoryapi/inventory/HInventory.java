package com.hakan.inventoryapi.inventory;

import com.hakan.inventoryapi.InventoryAPI;
import me.notnull.vauction.auction.Auction;
import me.notnull.vauction.inventory.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HInventory implements InventoryHolder, Cloneable {

    private final InventoryManager inventoryManager;
    private final Pagination pagination;
    private final Inventory bukkitInventory;
    private final String title;
    private final HashMap<Integer, ClickableItem> clickableItems = new HashMap<>();
    public Consumer<InventoryCloseEvent> closeChecker;
    private final String id;
    private boolean closable;
    private boolean clickable;


    private InventoryProvider provider;

    private final Map<String,Object> values;

    public HInventory(InventoryAPI inventoryAPI, String title, InventoryType inventoryType, int size, String id, boolean closable) {

        this.inventoryManager = inventoryAPI.getInventoryManager();
        this.pagination = new Pagination(this);

        this.values = new HashMap<>();
        this.clickable = false;

        this.id = id;
        this.closable = closable;
        this.title = title;
        this.bukkitInventory = inventoryType.equals(InventoryType.CHEST) ? Bukkit.createInventory(this, size * 9, title) : Bukkit.createInventory(this, inventoryType, title);
    }

    public void open(Player player) {
        player.openInventory(this.bukkitInventory);
        this.inventoryManager.setPlayerInventory(player, this);
    }

    public void setValue(final String key,final Object value){
        this.values.put(key,value);
    }

    public Object getValue(final String key){
        return values.get(key);
    }


    public void close(Player player) {
        this.closable = true;
        player.closeInventory();
    }

    public void guiFill(ClickableItem clickableItem) {
        for (int slot = 0; slot < this.getSize() * 9; slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public InventoryProvider getProvider(){return this.provider;}

    public void guiFill(ItemStack itemStack) {
        this.guiFill(ClickableItem.empty(itemStack));
    }

    public String getId() {
        return this.id;
    }

    public int getSize() {
        return this.bukkitInventory.getSize() / 9;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isClosable() {
        return this.closable;
    }

    public void setClosable(boolean closeable) {
        this.closable = closeable;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void setProvider(final InventoryProvider provider){
        this.provider = provider;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void whenClosed(Consumer<InventoryCloseEvent> closeChecker) {
        this.closeChecker = closeChecker;
    }

    public ClickableItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public void setItem(int slot, ClickableItem clickableItem) {
        if (clickableItem.getItem() == null || clickableItem.getItem().getType().equals(Material.AIR)) {
            this.clickableItems.put(slot, ClickableItem.empty(new ItemStack(Material.AIR)));
            this.bukkitInventory.setItem(slot, clickableItem.getItem());
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.bukkitInventory.setItem(slot, clickableItem.getItem());
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    @Override
    public Inventory getInventory() {
        return this.bukkitInventory;
    }

    @Override
    public HInventory clone() {
        try {
            return (HInventory) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}