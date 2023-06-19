package net.iambartz.lightparkour.api.repository;

public interface Record<ID, T extends Identifiable<ID>> extends Identifiable<ID> {
    T toTarget();
    int getFields();
    boolean isEmpty();
}
