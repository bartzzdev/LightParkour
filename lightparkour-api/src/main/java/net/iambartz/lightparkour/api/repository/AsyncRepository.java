package net.iambartz.lightparkour.api.repository;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncRepository<ID, R extends Record<ID, ? extends Identifiable<ID>>> extends Repository<ID, R> {
    CompletableFuture<R> findByIdAsync(ID id);
    CompletableFuture<Collection<R>> findAllAsync();
    CompletableFuture<R> saveAsync(R target);
    CompletableFuture<R> deleteAsync(ID id);
}
