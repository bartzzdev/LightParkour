package net.iambartz.lightparkour.paper;

import net.iambartz.lightparkour.api.LightParkourApi;
import net.iambartz.lightparkour.api.map.ParkourMap;
import net.iambartz.lightparkour.api.repository.Record;
import net.iambartz.lightparkour.api.repository.Repository;
import net.iambartz.lightparkour.paper.player.repository.PlayerRepository;
import net.iambartz.lightparkour.paper.player.service.PlayerService;

import java.util.UUID;

public final class LightParkourPaperApi implements LightParkourApi {
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    private LightParkourPaperApi() {
        this.playerRepository = new PlayerRepository();
        this.playerService = new PlayerService(playerRepository);
    }

    private static class LazyApiContainer {
        private static final LightParkourPaperApi INSTANCE = new LightParkourPaperApi();
    }

    public static LightParkourPaperApi get() {
        return LazyApiContainer.INSTANCE;
    }

    @Override
    public PlayerRepository playerRepository() {
        return playerRepository;
    }

    public PlayerService playerService() {
        return playerService;
    }

    @Override
    public Repository<UUID, ? extends Record<UUID, ? extends ParkourMap>> mapRepository() {
        return null;
    }
}
