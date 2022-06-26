package me.notnull.vauction.inventory;

public interface InventoryProvider {

    void init(final Object object);

    void update(final Object object);

    default void add(final Object... object) {}

    default void remove(final Object object) {}

    default void cancel(final Object object) {}

    default void confirm(final Object object) {}

}
