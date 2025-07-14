package org.example.dsl.validator;

import java.util.function.Predicate;

public class EmailValidator {

    private Predicate<String> emailPredicate =  predicate ->  true;

    public EmailValidator with(Predicate<String> emailPredicate) {
         this.emailPredicate = this.emailPredicate.and(emailPredicate);
        return this;
    }

    public boolean validate(String email) {
        return emailPredicate.test(email);
    }
}
