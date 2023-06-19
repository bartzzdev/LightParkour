package net.iambartz.lightparkour.paper.player;

import net.iambartz.lightparkour.api.player.PlayerSession;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class SimplePlayerSession implements PlayerSession {
    private final UUID id;
    private final String name;

    SimplePlayerSession(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SimplePlayerSession create(UUID id, String name) {
        return new SimplePlayerSession(id, name);
    }

    public static SimplePlayerSession createFromPlayer(@NotNull Player player) {
        Objects.requireNonNull(player, "Cannot construct a session out of a null player parameter");
        return new SimplePlayerSession(player.getUniqueId(), player.getName());
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
