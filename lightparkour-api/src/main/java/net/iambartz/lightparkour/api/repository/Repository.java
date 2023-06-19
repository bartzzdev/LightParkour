package net.iambartz.lightparkour.api.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<ID, R extends Record<ID, ? extends Identifiable<ID>>> {
    Optional<R> findById(ID id);
    Collection<R> findAll();
    R save(R target);
    R delete(ID id);
}
