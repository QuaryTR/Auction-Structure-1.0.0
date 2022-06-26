package me.notnull.vauction.config.utils;

public enum PlayerMessage {

    NO_PERMISSION("NO_PERMISSION"),
    BID_SUCCESS("BID_SUCCES"),
    OFFER_MUST_BIGGER_THAN_CURRENT_PRICE("OFFER_MUST_BIGGER_THAN_CURRENT_PRICE"),
    SOMEONE_BID_HIGHER_THAN_YOU("SOMEONE_BID_HIGHER_THAN_YOU"),
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE"),
    STORAGE_ITEM_DEPOSIT_SUCCESS("STORAGE_ITEM_DEPOSIT_SUCCESS"),
    STORAGE_MONEY_DEPOSIT_SUCCESS("STORAGE_MONEY_DEPOSIT_SUCCESS"),
    INVENTORY_NO_SPACE("INVENTORY_NO_SPACE");

    private String message;

    PlayerMessage(final String message){
        this.message = message;
    }

    public void setMessage(final String message){
        this.message = message;
    }

    public String getMessage(){return this.message;}






}
