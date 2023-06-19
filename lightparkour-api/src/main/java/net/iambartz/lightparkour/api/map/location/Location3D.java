package net.iambartz.lightparkour.api.map.location;

public interface Location3D {
    double getX();
    double getY();
    double getZ();
    double distance(Location3D location);
}
