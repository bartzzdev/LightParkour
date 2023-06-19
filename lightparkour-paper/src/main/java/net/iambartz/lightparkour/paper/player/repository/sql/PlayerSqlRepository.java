package net.iambartz.lightparkour.paper.player.repository.sql;

import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecord;
import net.iambartz.lightparkour.paper.player.repository.GamePlayerRecordBuilder;
import net.iambartz.lightparkour.paper.repository.SqlRepository;
import net.iambartz.lightparkour.paper.repository.query.QueryBuilder;
import net.iambartz.lightparkour.paper.repository.query.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public final class PlayerSqlRepository extends SqlRepository<UUID, GamePlayerRecord> {
    public PlayerSqlRepository(String tableName) {
        super(tableName);
    }

    private void logSqlException(SqlQuery query, SQLException exception) {
        logger.severe(String.format("The query: %s", query.toStringQuery()));
        logger.severe(String.format("The error message: %s", exception.getMessage()));
    }

    @Override
    public Optional<GamePlayerRecord> findById(UUID uuid) {
        var query = QueryBuilder.newBuilder(super.tableName)
                .fetch()
                .where("id")
                .build();
        try (var statement = super.prepareStatement(query)) {
            statement.setObject(1, uuid.toString());
            var resultSet = statement.executeQuery();
            return constructFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.severe(String.format("An error occurred on fetching game player record by id: %s",
                    uuid));
            logSqlException(query, e);
            return Optional.empty();
        }
    }

    @Override
    public Collection<GamePlayerRecord> findAll() {
        var query = QueryBuilder.newBuilder(super.tableName)
                .fetch()
                .build();
        try (var statement = super.prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            var records = new HashSet<GamePlayerRecord>(resultSet.getFetchSize());
            while (resultSet.next()) {
                constructFromResultSet(resultSet)
                        .ifPresent(records::add);
            }
            return records;
        } catch (SQLException e) {
            logger.severe("An error occurred on fetching all game players");
            logSqlException(query, e);
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<GamePlayerRecord> save(GamePlayerRecord record) {
        var query = QueryBuilder.newBuilder(super.tableName)
                .save("name")
                .build();
        try (var statement = super.prepareStatement(query)) {
            // insert initial values
            statement.setObject(1, record.getId().toString());
            statement.setObject(2, record.getName());
            // on key existing update
            statement.setObject(3, record.getName());
            statement.executeUpdate();
            return Optional.of(record);
        } catch (SQLException e) {
            logger.severe(String.format("An error occurred on saving game player record: %s",
                    record));
            logSqlException(query, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<GamePlayerRecord> delete(UUID uuid) {
        return Optional.empty();
    }

    @Override
    protected Optional<GamePlayerRecord> constructFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }

        var id = UUID.fromString(resultSet.getString("id"));
        var name = resultSet.getString("name");
        return Optional.ofNullable(GamePlayerRecordBuilder.newBuilder()
                .id(id)
                .name(name)
                .build());
    }
}
