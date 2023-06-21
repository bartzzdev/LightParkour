package net.iambartz.lightparkour.paper.player;

import net.iambartz.lightparkour.api.player.PlayerSession;
import net.iambartz.lightparkour.paper.player.effect.Effect;
import net.iambartz.lightparkour.paper.player.effect.EffectApplicable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class GamePlayer implements PlayerSession, PhysicalPlayerAppearance, EffectApplicable<GamePlayer> {
    private final PlayerSession playerSession;
    private Player bukkitPlayer;

    GamePlayer(UUID id, String name) {
        this.playerSession = SimplePlayerSession.create(id, name);
    }

    GamePlayer(PlayerSession playerSession) {
        this.playerSession = playerSession;
    }

    public static GamePlayer create(UUID id, String name) {
        return new GamePlayer(id, name);
    }

    public static GamePlayer createFromPlayer(@NotNull Player player) {
        Objects.requireNonNull(player, "Cannot construct a game player out of a null player parameter");
        return new GamePlayer(player.getUniqueId(), player.getName());
    }

    public static GamePlayer createFromSession(@NotNull PlayerSession playerSession) {
        Objects.requireNonNull(playerSession, "Cannot construct a game player out of a null session parameter");
        return new GamePlayer(playerSession);
    }

    @Override
    public UUID getId() {
        return playerSession.getId();
    }

    @Override
    public String getName() {
        return playerSession.getName();
    }

    @Override
    public Player getPlayer() {
        if (this.bukkitPlayer == null) {
            throw new RuntimeException("The physical player instance is not set for this game player");
        }
        return this.bukkitPlayer;
    }

    @Override
    public void setPlayer(Player player) {
        this.bukkitPlayer = player;
    }

    @Override
    public void applyEffect(Effect<GamePlayer> effect) {
        effect.spawn(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayer that = (GamePlayer) o;
        return Objects.equals(playerSession, that.playerSession) && Objects.equals(bukkitPlayer, that.bukkitPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerSession, bukkitPlayer);
    }
}
