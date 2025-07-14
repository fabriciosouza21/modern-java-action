package org.example.dsl.emal;

public class EmailBuild {
    private String to;

    private String subject;

    private String body;

    private Priority priority;

    public EmailBuild to(String to) {
        this.to = to;
        return this;
    }

    public EmailBuild subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuild body(String body) {
        this.body = body;
        return this;
    }

    public EmailBuild priority(Priority priority) {
        this.priority = priority;
        return this;
    }
    public Email build() {
        return new Email(to, subject, body, priority);
    }
}
