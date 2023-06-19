package net.iambartz.lightparkour.api;

import net.iambartz.lightparkour.api.map.ParkourMap;
import net.iambartz.lightparkour.api.player.PlayerSession;
import net.iambartz.lightparkour.api.repository.Record;
import net.iambartz.lightparkour.api.repository.Repository;

import java.util.UUID;

public interface LightParkourApi {
    Repository<UUID, ? extends Record<UUID, ? extends PlayerSession>> playerRepository();
    Repository<UUID, ? extends Record<UUID, ? extends ParkourMap>> mapRepository();
}
