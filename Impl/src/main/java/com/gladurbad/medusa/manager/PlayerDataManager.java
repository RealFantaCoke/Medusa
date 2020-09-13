package com.gladurbad.medusa.manager;

import com.gladurbad.medusa.playerdata.PlayerData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerDataManager {

    private static final PlayerDataManager INSTANCE = new PlayerDataManager();

    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public boolean containsPlayer(Player player) {
        return containsPlayer(player.getUniqueId());
    }

    public boolean containsPlayer(UUID playerUUID) {
        return getInstance().getPlayerData().containsKey(playerUUID);
    }

    public PlayerData getPlayerData(Player player) {
        return getInstance().playerData.get(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID playerUUID) {
        return getInstance().playerData.get(playerUUID);
    }

    public static PlayerDataManager getInstance() {
        return INSTANCE;
    }

}
