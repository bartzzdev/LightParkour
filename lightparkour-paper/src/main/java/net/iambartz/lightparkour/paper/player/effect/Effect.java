package net.iambartz.lightparkour.paper.player.effect;

import net.iambartz.lightparkour.paper.player.PhysicalPlayerAppearance;

public interface Effect<P extends PhysicalPlayerAppearance> {
    void spawn(P player);
}
