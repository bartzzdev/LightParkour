package net.iambartz.lightparkour.paper.repository.query;

import net.iambartz.lightparkour.api.util.builder.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryBuilder {
    private final String tableName;

    private QueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public static QueryBuilder newBuilder(String tableName) {
        return new QueryBuilder(tableName);
    }

    public FetchQueryBuilder fetch(String... params) {
        return new FetchQueryBuilder(tableName, params);
    }

    public SaveQueryBuilder save(String... params) {
        return new SaveQueryBuilder(tableName, params);
    }

    public static class SaveQueryBuilder implements Builder<SqlQuery> {
        private final String table;
        private final SaveQueryParams params;

        private SaveQueryBuilder(@NotNull String table, String... saveParams) {
            this.table = Objects.requireNonNull(table, "A null table parameter provided in query");
            this.params = new SaveQueryParams(saveParams);
        }

        @Override
        public SqlQuery build() {
            var values = Arrays.stream(params.params)
                    .map(it -> "?")
                    .collect(Collectors.joining(","));
            var columnsSet = Arrays.stream(params.params)
                    .map(it -> it + " = ?")
                    .collect(Collectors.joining(","));
            return () -> "INSERT INTO " + table + " (id, " + params.build().toStringQuery() + ")" +
                    " VALUES (?, " + values + ")" +
                     " ON CONFLICT (id) DO UPDATE SET " + columnsSet;

        }
    }

    public static class FetchQueryBuilder implements Builder<SqlQuery> {
        private final String table;
        private final FetchQueryParams params;

        private FetchQueryBuilder(@NotNull String table, String... fetchParams) {
            this.table = Objects.requireNonNull(table, "A null table parameter provided in query");
            if (fetchParams == null || fetchParams.length == 0) {
                this.params = new FetchQueryParams("*");
            } else {
                this.params = new FetchQueryParams(fetchParams);
            }
        }

        public WhereClauseBuilder where(String... params) {
            return new WhereClauseBuilder(this, params);
        }

        @Override
        public SqlQuery build() {
            return () -> "SELECT " + params.build().toStringQuery() + " FROM " + table;
        }
    }

    private static abstract class QueryParams implements Builder<SqlQuery> {
        protected final String[] params;

        private QueryParams(String... params) {
            this.params = params;
        }

        @Override
        public abstract SqlQuery build();
    }

    private static final class SaveQueryParams extends QueryParams {
        private SaveQueryParams(String... params) {
            super(params);
        }

        @Override
        public SqlQuery build() {
            return () -> String.join(",", params);
        }
    }

    private static final class FetchQueryParams extends QueryParams {
        private FetchQueryParams(String... params) {
            super(params);
        }

        @Override
        public SqlQuery build() {
            return () -> String.join(",", params);
        }
    }

    private static final class WhereQueryParams extends QueryParams {
        private WhereQueryParams(String... params) {
            super(params);
        }

        @Override
        public SqlQuery build() {
            return () -> Arrays.stream(params)
                    .map(it -> it + " = ?")
                    .collect(Collectors.joining(" AND "));
        }
    }

    public static class WhereClauseBuilder implements Builder<SqlQuery> {
        private final FetchQueryBuilder fetchQuery;
        private final WhereQueryParams params;

        private WhereClauseBuilder(FetchQueryBuilder fetchQuery, String... params) {
            this.fetchQuery = fetchQuery;
            this.params = new WhereQueryParams(params);
        }

        @Override
        public SqlQuery build() {
            return () -> fetchQuery.build().toStringQuery() + " WHERE " + params.build().toStringQuery();
        }
    }
}
