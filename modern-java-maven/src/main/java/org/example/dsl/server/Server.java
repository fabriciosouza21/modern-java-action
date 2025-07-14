package org.example.dsl.server;

public class Server {

    private final int port;

    private final boolean enableSsl;

    private final boolean ennableLogging;

    private final boolean enableCompression;

    public Server(int port, boolean enableSsl, boolean ennableLogging, boolean enableCompression) {
        this.port = port;
        this.enableSsl = enableSsl;
        this.ennableLogging = ennableLogging;
        this.enableCompression = enableCompression;
    }


    public int getPort() {
        return port;
    }

    public boolean isEnableSsl() {
        return enableSsl;
    }

    public boolean isEnnableLogging() {
        return ennableLogging;
    }

    public boolean isEnableCompression() {
        return enableCompression;
    }
}
