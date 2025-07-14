package org.example.dsl.query.domain;

public class FromClause {

    private String table;

    public FromClause(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}
