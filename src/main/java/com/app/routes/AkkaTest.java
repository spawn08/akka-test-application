package com.app.routes;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static com.app.utils.Utils.getAuth;
import static com.app.utils.Utils.getResponse;

public class AkkaTest {

    public static Route createRoute() {
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
