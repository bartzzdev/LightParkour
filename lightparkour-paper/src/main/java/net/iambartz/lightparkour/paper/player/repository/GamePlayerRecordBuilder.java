package net.iambartz.lightparkour.paper.player.repository;

import net.iambartz.lightparkour.api.util.builder.Builder;

import java.util.UUID;

public class GamePlayerRecordBuilder implements Builder<GamePlayerRecord> {
    private UUID id;
    private String name;

    private GamePlayerRecordBuilder() {}
    
    public static GamePlayerRecordBuilder newBuilder() {
        return new GamePlayerRecordBuilder();
    }

    public GamePlayerRecordBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public GamePlayerRecordBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GamePlayerRecord build() {
        return new GamePlayerRecord(this.id, this.name);
    }
}
