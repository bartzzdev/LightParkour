package net.iambartz.lightparkour.api.map.performance;

import net.iambartz.lightparkour.api.player.PlayerSession;

public interface PerformanceScore<R, P extends PlayerSession> extends Comparable<PerformanceScore<R, P>> {
    R getResult();
    P getSession();
}
