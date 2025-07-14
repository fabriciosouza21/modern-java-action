package org.example.dsl.query.domain;

public class EqualsCondition extends Condition{

    private String field;
    private Object value;

    public EqualsCondition(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public boolean evaluate(User user) {
        return switch (field) {
            case "name" -> user.getName().equals(value);
            case "age" -> user.getAge() == (int) value;
            case "active" -> user.isActive() == (boolean) value;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }
}
