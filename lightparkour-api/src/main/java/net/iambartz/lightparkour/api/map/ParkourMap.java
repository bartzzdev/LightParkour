package net.iambartz.lightparkour.api.map;

import net.iambartz.lightparkour.api.repository.Identifiable;

import java.util.UUID;

public interface ParkourMap extends Identifiable<UUID>, Localizable {
    MapInfo getInfo();
}
