package org.example.dsl.query;

import org.example.dsl.query.domain.Condition;
import org.example.dsl.query.domain.EqualsCondition;
import org.example.dsl.query.domain.FromClause;
import org.example.dsl.query.domain.SelectClause;
import org.example.dsl.query.domain.User;

import java.util.Arrays;
import java.util.List;

public class QueryDSL {

    private static List<User> DATABASE =  Arrays.asList(
            new User("Alice", 25, true),
            new User("Bob", 17, true),
            new User("Charlie", 30, false),
            new User("Diana", 22, true)
    );


    public static List<User> query (SelectClause select, FromClause from, Condition where) {
        return DATABASE.stream().filter(where::evaluate).toList();
    }

    public static SelectClause select(String... fields) {
        return new SelectClause(fields);
    }

    public static FromClause from(String table){
        return new FromClause(table);
    }

    public static Condition equals(String field, Object value){
        return new EqualsCondition(field, value);
    }
}
