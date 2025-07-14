package org.example.dsl.query.domain;

public class SelectClause {

    private final String[] fields;

    public SelectClause(String... fields) {
        this.fields = fields;
    }

    public String[] getFields() {
        return fields;
    }
}
