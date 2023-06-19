package net.iambartz.lightparkour.paper.player.repository;

import net.iambartz.lightparkour.api.repository.AsyncRepository;
import net.iambartz.lightparkour.paper.player.repository.sql.PlayerSqlRepository;
import net.iambartz.lightparkour.paper.repository.SqlRepository;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public final class PlayerRepository implements AsyncRepository<UUID, GamePlayerRecord> {
    private final Logger logger = Logger.getLogger("LightParkour");
    private final SqlRepository<UUID, GamePlayerRecord> sqlRepository = new PlayerSqlRepository("players");

    @Override
    public CompletableFuture<GamePlayerRecord> findByIdAsync(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> this.findById(uuid)
                .orElse(GamePlayerRecord.EMPTY_RECORD));
    }

    @Override
    public CompletableFuture<Collection<GamePlayerRecord>> findAllAsync() {
        return CompletableFuture.supplyAsync(this::findAll);
    }

    @Override
    public CompletableFuture<GamePlayerRecord> saveAsync(GamePlayerRecord target) {
        return CompletableFuture.supplyAsync(() -> this.save(target));
    }

    @Override
    public CompletableFuture<GamePlayerRecord> deleteAsync(UUID uuid) {
        return null;
    }

    @Override
    public Optional<GamePlayerRecord> findById(UUID uuid) {
        this.logger.info(String.format("Fetching player record for id: %s", uuid));
        return sqlRepository.findById(uuid);
    }

    @Override
    public Collection<GamePlayerRecord> findAll() {
        this.logger.info("Fetching all player records");
        return this.sqlRepository.findAll();
    }

    @Override
    public GamePlayerRecord save(GamePlayerRecord target) {
        this.logger.info(String.format("Saving player record: %s", target));
        this.sqlRepository.save(target);
        return target;
    }

    @Override
    public GamePlayerRecord delete(UUID uuid) {
        this.logger.info(String.format("Deleting player record for id: %s", uuid));
//        return this.players.remove(uuid);
        return null;
    }
}
