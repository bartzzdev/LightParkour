package net.iambartz.lightparkour.paper.repository;

import com.zaxxer.hikari.HikariConfig;
import net.iambartz.lightparkour.paper.repository.query.SqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public final class HikariDataSource {
    private static final HikariConfig CONFIG;
    private static final com.zaxxer.hikari.HikariDataSource DATA_SOURCE;

    static {
        var properties = new Properties();
        properties.setProperty("dataSource.user", "light_parkour");
        properties.setProperty("dataSource.password", "secret");
        properties.setProperty("dataSource.databaseName", "light_parkour");
        properties.setProperty("dataSource.serverName", "parkour-database");
        properties.setProperty("dataSourceClassName", "com.impossibl.postgres.jdbc.PGDataSource");
        CONFIG = new HikariConfig(properties);
        DATA_SOURCE = new com.zaxxer.hikari.HikariDataSource(CONFIG);

    }

    public Connection connection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    public PreparedStatement prepareStatement(SqlQuery query) throws SQLException {
        return DATA_SOURCE.getConnection().prepareStatement(query.toStringQuery());
    }
}
