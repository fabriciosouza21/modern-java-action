package org.example.dsl.server;

import java.util.function.Consumer;

public class ServerCreate {

    public static Server serverCreate(Consumer<ServerBuilder> configurator){
        ServerBuilder serverBuilder = new ServerBuilder();
        configurator.accept(serverBuilder);
        return serverBuilder.build();
    }
}
