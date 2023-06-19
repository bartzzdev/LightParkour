package net.iambartz.lightparkour.api.map.performance;

import java.util.Collection;

public interface PerformanceRegistry<P extends PerformanceScore<?, ?>> {
    P getScoreByPosition(int position);

    Collection<P> getTopScores(int limit);

    Collection<P> getTopScores();
}
