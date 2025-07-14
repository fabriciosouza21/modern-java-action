package org.example.dsl.emal;

public class Email {

    private String to;

    private String subject;

    private String body;

    private Priority priority;

    public Email(String to, String subject, String body, Priority priority) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.priority = priority;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", priority=" + priority +
                '}';
    }
}
