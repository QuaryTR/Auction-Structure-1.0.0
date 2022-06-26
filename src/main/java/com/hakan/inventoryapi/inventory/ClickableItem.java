package com.hakan.inventoryapi.inventory;

import com.hakan.inventoryapi.customevents.HInventoryClickEvent;
import me.notnull.vauction.inventory.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ClickableItem {

    private ItemStack item;
    private Consumer<HInventoryClickEvent> click;

    private final Map<String,Object> values;

    private ClickableItem(ItemStack item, Consumer<HInventoryClickEvent> click) {
        this.item = item;
        this.click = click;
        this.values = new HashMap<>();
    }

    public static ClickableItem of(ItemStack item, Consumer<HInventoryClickEvent> click) {
        return new ClickableItem(item, click);
    }

    public static ClickableItem empty(ItemStack item) {
        return of(item, null);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void addValue(final String key,final Object value){values.put(key,value);}

    public Object getValue(final String key){return values.get(key);}

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Consumer<HInventoryClickEvent> getClick() {
        return this.click;
    }

    public void setClick(Consumer<HInventoryClickEvent> click) {
        this.click = click;
    }

    public void setLore(final List<String> lore){this.item = new ItemBuilder(item).setLore(lore).toItemStack();}





}
