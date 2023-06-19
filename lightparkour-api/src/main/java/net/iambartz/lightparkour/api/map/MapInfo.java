package net.iambartz.lightparkour.api.map;

public interface MapInfo {
    String getName();

    String getDescription();

    String getAuthor();

    void setName(String name);

    void setDescription(String description);

    void setAuthor(String author);
}
