package net.iambartz.lightparkour.paper.player.service;

import net.iambartz.lightparkour.paper.player.GamePlayer;
import net.iambartz.lightparkour.paper.player.effect.FirstJoinEffect;
import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecord;
import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecordBuilder;
import net.iambartz.lightparkour.paper.player.repository.PlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public final class PlayerService {
    private final PlayerRepository playerRepository;
    private final Map<UUID, GamePlayer> livePlayers = new ConcurrentHashMap<>();

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<GamePlayer> findById(UUID id) {
        return playerRepository.findById(id)
                .map(GamePlayerRecord::toTarget);
    }

    private static final FirstJoinEffect FIRST_JOIN_EFFECT = FirstJoinEffect.FirstJoinEffectBuilder.newBuilder().build();

    /**
     * Handles the {@link PlayerJoinEvent}. Creates a new GamePlayer instance for the player
     * and stores their data in the database if a record does not exist.
     * @param event the PlayerJoinEvent to handle
     */
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        var logger = Logger.getLogger("LightParkour");
        var player = event.getPlayer();
        var playerId = player.getUniqueId();
        long start = System.currentTimeMillis();
        playerRepository.findByIdAsync(playerId)
                .thenCompose(record -> {
                    if (record.isEmpty()) {
                        return storeNewPlayerData(player);
                    } else {
                        return CompletableFuture.completedFuture(record);
                    }
                })
                .thenApplyAsync(GamePlayerRecord::toTarget)
                .thenAccept(it -> {
                    it.setPlayer(player);
                    it.applyEffect(FIRST_JOIN_EFFECT);
                    logger.info("Applying an effect to the player.");
                })
                .exceptionallyAsync(throwable -> {
                    logger.severe(throwable.getMessage());
                    return null;
                })
                .whenComplete((result, ex) -> {
                    long elapsedTime = System.currentTimeMillis() - start;
                    logger.info(String.format("Time spent on the query: %s ms.", elapsedTime));
                });
    }

    private CompletableFuture<GamePlayerRecord> storeNewPlayerData(Player player) {
        return playerRepository.saveAsync(GamePlayerRecordBuilder.newBuilder()
                .id(player.getUniqueId())
                .name(player.getName())
                .build());
    }

    public void save(GamePlayerRecord record) {
        playerRepository.save(record);
    }
}
