package org.example.dsl;

import org.example.dsl.discount.DiscountCalculator;
import org.example.dsl.discount.Discounts;
import org.example.dsl.emal.Email;
import org.example.dsl.emal.EmailDsl;
import org.example.dsl.emal.Priority;
import org.example.dsl.query.QueryDSL;
import org.example.dsl.query.domain.User;
import org.example.dsl.server.Server;
import org.example.dsl.server.ServerBuilder;
import org.example.dsl.server.ServerCreate;
import org.example.dsl.validator.EmailRules;
import org.example.dsl.validator.EmailValidator;

import java.util.List;

import static org.example.dsl.emal.EmailDsl.*;
import static org.example.dsl.query.QueryDSL.from;
import static org.example.dsl.query.QueryDSL.query;
import static org.example.dsl.query.QueryDSL.select;


public class DSL {

    public static void exec(){
        Server server = new ServerBuilder().port(8080).enableSsl().enableLogging().enableCompression().build();

        double discountedPrice = new DiscountCalculator().with(Discounts::student).with(Discounts::loyatlty).calculate(100.0);

        boolean valid = new EmailValidator().with(EmailRules::hasAtSymbol).with(EmailRules::hasMinLength).with(EmailRules::hasValidDomain).validate("user@example.com");

        List<User> activeUsers = query(
                select("name"),
                from("users"),
                QueryDSL.equals("active", true)
        );

        activeUsers.forEach(System.out::println);

        Email email = createEmail(config -> {
            config.to("john@example.com");
            config.subject("Welcome!");
            config.body("Hello John, welcome to our service.");
            config.priority(Priority.HIGH);
        });

        Server serverConsumer = ServerCreate.serverCreate(config -> {
            config.port(9090)
                    .enableSsl()
                    .enableLogging()
                    .enableCompression();
        });

        System.out.println("Server Port: " + server.getPort());
        System.out.println("Discounted Price: " + discountedPrice);
        System.out.println("Is valid email: " + valid);
        System.out.println("Email: " + email);
        System.out.println("Server Consumer Port: " + serverConsumer.getPort());
    }
}
