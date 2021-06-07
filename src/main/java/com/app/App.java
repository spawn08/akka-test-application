package com.app;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import com.app.routes.AkkaTest;
import com.app.service.KafkaService;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class App {

    public static void main(String[] args) throws IOException {
        ClassicActorSystemProvider system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        KafkaService kafkaService = new KafkaService(system);
        final CompletionStage<ServerBinding> binding =
                http.newServerAt("localhost", 8080)
                        .bind(AkkaTest.createRoute(kafkaService));
        new KafkaService(system);
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.classicSystem().terminate()); // and shutdown when done
    }
}
