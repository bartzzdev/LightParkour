package net.iambartz.lightparkour.paper.player.service;

import net.iambartz.lightparkour.paper.player.GamePlayer;
import net.iambartz.lightparkour.paper.player.effect.FirstJoinEffect;
import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecord;
import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecordBuilder;
import net.iambartz.lightparkour.paper.player.repository.PlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

    /**
     * Handles the {@link PlayerJoinEvent}. Creates a new GamePlayer instance for the player
     * and stores their data in the database if a record does not exist.
     * @param event the PlayerJoinEvent to handle
     */
    public void loadOnJoin(PlayerJoinEvent event) {
        var logger = Logger.getLogger("LightParkour");
        var player = event.getPlayer();
        var playerId = player.getUniqueId();
        long start = System.currentTimeMillis();
        var gamePlayer = playerRepository.findById(playerId)
                .map(GamePlayerRecord::toTarget)
                .orElse(storeNewPlayerData(player).toTarget());
        this.addLivePlayer(gamePlayer);
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info(String.format("Time spent on the query: %s ms.", elapsedTime));
//        playerRepository.findByIdAsync(playerId)
//                .thenCompose(record -> {
//                    if (record.isEmpty()) {
//                        return storeNewPlayerData(player);
//                    } else {
//                        return CompletableFuture.completedFuture(record);
//                    }
//                })
//                .thenApplyAsync(GamePlayerRecord::toTarget)
//                .thenAcceptAsync(this::addLivePlayer)
//                .exceptionallyAsync(throwable -> {
//                    logger.severe(throwable.getMessage());
//                    return null;
//                })
//                .whenComplete((result, ex) -> {
//                    long elapsedTime = System.currentTimeMillis() - start;
//                    logger.info(String.format("Time spent on the query: %s ms.", elapsedTime));
//                });
    }


    private void addLivePlayer(GamePlayer player) {
        this.livePlayers.put(player.getId(), player);
    }

    private GamePlayer removeLivePlayer(Player player) {
        return this.livePlayers.remove(player.getUniqueId());
    }

    public void saveOnQuit(PlayerQuitEvent event) {
        var logger = Logger.getLogger("LightParkour");
        var start = System.currentTimeMillis();
        var player = removeLivePlayer(event.getPlayer());
        if (player == null) {
            System.out.println("An error occurred!");
            // handle error
            return;
        }

        this.playerRepository.save(GamePlayerRecordBuilder.newBuilder()
                .id(player.getId())
                .name(player.getName())
                .build());
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info(String.format("Time spent on the query: %s ms.", elapsedTime));
//        this.playerRepository.saveAsync(GamePlayerRecordBuilder.newBuilder()
//                .id(player.getId())
//                .name(player.getName())
//                .build())
//                .whenComplete((result, ex) -> {
//                    long elapsedTime = System.currentTimeMillis() - start;
//                    logger.info(String.format("Time spent on the query: %s ms.", elapsedTime));
//                });
    }

    private CompletableFuture<GamePlayerRecord> storeNewPlayerDataAsync(Player player) {
        return playerRepository.saveAsync(GamePlayerRecordBuilder.newBuilder()
                .id(player.getUniqueId())
                .name(player.getName())
                .build());
    }

    private GamePlayerRecord storeNewPlayerData(Player player) {
        return playerRepository.save(GamePlayerRecordBuilder.newBuilder()
                .id(player.getUniqueId())
                .name(player.getName())
                .build());
    }
}
