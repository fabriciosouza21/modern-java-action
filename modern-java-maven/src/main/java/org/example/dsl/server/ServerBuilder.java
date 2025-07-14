package org.example.dsl.server;

public class ServerBuilder {

    private int port;

    private boolean enableSsl;

    private boolean enableLogging;

    private boolean enableCompression;

    public ServerBuilder() {
    }

    public ServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public ServerBuilder enableSsl() {
        this.enableSsl = true;
        return this;
    }

    public ServerBuilder enableLogging() {
        this.enableLogging = true;
        return this;
    }

    public ServerBuilder enableCompression() {
        this.enableCompression = true;
        return this;
    }

    public Server build() {
        return new Server(port, enableSsl, enableLogging, enableCompression);
    }





}
