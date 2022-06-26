package me.notnull.vauction.config.utils;

public enum SystemMessage {

    VAULT_PLUGIN_NOT_FOUND("VAULT_PLUGIN_NOT_FOUND"),
    ECONOMY_PROVIDER_NOT_FOUND("ECONOMY_PROVIDER_NOT_FOUND"),
    VAULT_SETUP_SUCCESS("VAULT_SETUP_SUCCESS");

    private String message;

    SystemMessage(final String message){
        this.message = message;
    }

    public void setMessage(final String message){
        this.message = message;
    }

    public String getMessage(){return this.message;}
}
