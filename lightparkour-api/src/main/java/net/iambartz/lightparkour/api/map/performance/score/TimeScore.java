package net.iambartz.lightparkour.api.map.performance.score;

import net.iambartz.lightparkour.api.map.performance.PerformanceScore;
import net.iambartz.lightparkour.api.player.PlayerSession;

public class TimeScore<P extends PlayerSession> implements PerformanceScore<Long, P> {
    private final P session;
    private final Long result;

    TimeScore(P session, Long result) {
        this.session = session;
        this.result = result;
    }

    public static <P extends PlayerSession> TimeScore<P> createScore(P session, Long result) {
        return new TimeScore<>(session, result);
    }

    @Override
    public Long getResult() {
        return this.result;
    }

    @Override
    public P getSession() {
        return this.session;
    }

    @Override
    public int compareTo(PerformanceScore<Long, P> o) {
        var comparisonResult = result.compareTo(o.getResult());
        if (comparisonResult == 0) {
            return session.getName().compareTo(o.getSession().getName());
        }
        return comparisonResult;
    }
}
