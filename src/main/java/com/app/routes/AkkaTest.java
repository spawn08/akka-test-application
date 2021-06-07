package com.app.routes;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import com.app.model.EventBody;
import com.app.model.Response;
import com.app.service.KafkaService;
import com.app.utils.Utils;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static com.app.utils.Utils.getAuth;
import static com.app.utils.Utils.getResponse;

public class AkkaTest {

    public static Route createRoute(KafkaService kafkaService) {
        return concat(
                path("hello", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))),
                post(() -> pathPrefix("v1",
                        () -> path("logevents",
                                () -> headerValueByName("Authorization", (auth) -> entity(Jackson.unmarshaller(EventBody.class), body -> {
                                    if (getAuth(auth).equals(Utils.AUTH_TOKEN)) {
                                        CompletionStage<String> pushLogs = kafkaService.pushLogs(body);
                                        return onSuccess(pushLogs, mayBeItem -> complete(StatusCodes.OK, getResponse("success"), Jackson.marshaller()));
                                    } else
                                        return complete(StatusCodes.UNAUTHORIZED, new Response("error"), Jackson.marshaller());
                                }))))));
    }
}
