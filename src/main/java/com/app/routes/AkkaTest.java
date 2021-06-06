package com.app.routes;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static com.app.utils.Utils.getAuth;
import static com.app.utils.Utils.getResponse;

public class AkkaTest extends AllDirectives {

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        AkkaTest app = new AkkaTest();

        final CompletionStage<ServerBinding> binding =
                http.newServerAt("localhost", 8080)
                        .bind(app.createRoute());

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }

    private Route createRoute() {
        return concat(
                path("hello", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))),
                post(() -> pathPrefix("v1",
                        () -> path("logevents",
                                () -> headerValueByName("Authorization", (auth) -> {
                                    final CompletionStage<String> futureMaybeItem = getAuth(auth);
                                    return onSuccess(futureMaybeItem, mayBeItem -> complete(StatusCodes.OK, getResponse(mayBeItem), Jackson.marshaller()));

                                })))));
    }
}
