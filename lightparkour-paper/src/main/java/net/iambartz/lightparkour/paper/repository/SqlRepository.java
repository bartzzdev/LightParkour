package net.iambartz.lightparkour.paper.repository;

import net.iambartz.lightparkour.api.repository.Identifiable;
import net.iambartz.lightparkour.api.repository.Record;
import net.iambartz.lightparkour.paper.repository.query.SqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class SqlRepository<ID, R extends Record<ID, ? extends Identifiable<ID>>> {
    private final HikariDataSource dataSource = new HikariDataSource();
    protected final Logger logger = Logger.getLogger("LightParkour");
    protected final String tableName;

    public SqlRepository(String tableName) {
        this.tableName = tableName;
    }

    protected PreparedStatement prepareStatement(SqlQuery query) throws SQLException {
        return dataSource.prepareStatement(query);
    }

    public abstract Optional<R> findById(ID id);

    public abstract Collection<R> findAll();

    public abstract Optional<R> save(R record);

    public abstract Optional<R> delete(ID id);

    protected abstract Optional<R> constructFromResultSet(ResultSet resultSet) throws SQLException;
}
