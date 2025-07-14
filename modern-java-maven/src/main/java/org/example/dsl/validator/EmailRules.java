package org.example.dsl.validator;

public class EmailRules {

    public static boolean hasAtSymbol(String email) {
        return email.contains("@");
    }

    public static boolean hasValidDomain(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String domain = parts[1];
        return domain.contains(".") && !domain.startsWith(".") && !domain.endsWith(".");
    }

    public static boolean hasMinLength(String email) {
        return email.length() >= 5; // Example minimum length
    }
}
