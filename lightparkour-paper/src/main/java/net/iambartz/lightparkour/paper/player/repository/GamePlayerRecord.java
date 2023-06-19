package net.iambartz.lightparkour.paper.player.repository;

import net.iambartz.lightparkour.api.repository.Record;
import net.iambartz.lightparkour.api.util.builder.Builder;
import net.iambartz.lightparkour.paper.player.GamePlayer;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public final class GamePlayerRecord implements Record<UUID, GamePlayer> {
    static final GamePlayerRecord EMPTY_RECORD = GamePlayerRecordBuilder.newBuilder().build();
    private final UUID id;
    private final String name;

    GamePlayerRecord(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GamePlayerRecord create(Builder<GamePlayerRecord> builder) {
        var record = builder.build();
        return new GamePlayerRecord(record.id, record.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayerRecord that = (GamePlayerRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GamePlayerRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    @Override
    public GamePlayer toTarget() {
        return GamePlayer.create(this.id, this.name);
    }

    @Override
    public int getFields() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return this.equals(EMPTY_RECORD);
    }
}
