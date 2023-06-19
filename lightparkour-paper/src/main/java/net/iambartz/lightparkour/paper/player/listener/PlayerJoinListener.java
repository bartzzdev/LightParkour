package net.iambartz.lightparkour.paper.player.listener;

import net.iambartz.lightparkour.paper.LightParkourPaperApi;
import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecordBuilder;
import net.iambartz.lightparkour.paper.player.service.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {
    private final PlayerService playerService;

    public PlayerJoinListener() {
        this.playerService = LightParkourPaperApi.get().playerService();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerService.handlePlayerJoinEvent(event);
    }
}
