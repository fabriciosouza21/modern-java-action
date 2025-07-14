package org.example.dsl.emal;

import java.util.function.Consumer;

public class EmailDsl {

    public static Email createEmail(Consumer<EmailBuild> configurator) {
        EmailBuild emailBuild = new EmailBuild();
        configurator.accept(emailBuild);
        return emailBuild.build();
    }
}
