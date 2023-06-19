package net.iambartz.lightparkour.api.player;

import net.iambartz.lightparkour.api.repository.Identifiable;

import java.util.UUID;

public interface PlayerSession extends Identifiable<UUID> {
    String getName();
}
