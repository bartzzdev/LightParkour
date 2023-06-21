package net.iambartz.lightparkour.paper.player.listener;

import net.iambartz.lightparkour.paper.LightParkourPaperApi;
import net.iambartz.lightparkour.paper.player.service.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerSessionListener implements Listener {
    private final PlayerService playerService;

    public PlayerSessionListener() {
        this.playerService = LightParkourPaperApi.get().playerService();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerService.loadOnJoin(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerService.saveOnQuit(event);
    }
}
