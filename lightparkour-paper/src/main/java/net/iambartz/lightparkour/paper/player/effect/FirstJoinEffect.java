package net.iambartz.lightparkour.paper.player.effect;

import net.iambartz.lightparkour.api.util.builder.Builder;
import net.iambartz.lightparkour.paper.player.GamePlayer;
import org.bukkit.Sound;

public final class FirstJoinEffect implements Effect<GamePlayer> {
    private final float volume;
    private final float pitch;

    private FirstJoinEffect(float volume, float pitch) {
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void spawn(GamePlayer player) {
        var physicalPlayer = player.getPlayer();
        physicalPlayer.playSound(physicalPlayer.getLocation(), Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, volume, pitch);
    }

    public static final class FirstJoinEffectBuilder implements Builder<FirstJoinEffect> {
        private float volume = 1f;
        private float pitch = .7f;

        private FirstJoinEffectBuilder() {}

        public static FirstJoinEffectBuilder newBuilder() {
            return new FirstJoinEffectBuilder();
        }

        public FirstJoinEffectBuilder volume(float volume) {
            this.volume = volume;
            return this;
        }

        public FirstJoinEffectBuilder pitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        @Override
        public FirstJoinEffect build() {
            return new FirstJoinEffect(volume, pitch);
        }
    }
}
