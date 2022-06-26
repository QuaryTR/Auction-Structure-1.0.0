package net.notnull.test;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface ServerPlayer {

    Map<UUID,ServerPlayer> PLAYER_DATA = new ConcurrentHashMap<>();

    static ServerPlayer getPlayer(final UUID playerUUID){return PLAYER_DATA.get(playerUUID);}

    static void setPlayer(final UUID key,final ServerPlayer value){PLAYER_DATA.put(key,value);}
}
