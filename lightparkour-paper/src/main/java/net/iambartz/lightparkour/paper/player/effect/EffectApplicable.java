package net.iambartz.lightparkour.paper.player.effect;

import net.iambartz.lightparkour.paper.player.PhysicalPlayerAppearance;

public interface EffectApplicable<P extends PhysicalPlayerAppearance> {
    void applyEffect(Effect<P> effect);
}
