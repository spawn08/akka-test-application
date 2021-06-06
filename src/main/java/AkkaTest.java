import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
                                    return onSuccess(futureMaybeItem, mayBeItem -> complete(StatusCodes.OK, getJson(mayBeItem), Jackson.marshaller()));

                                })))));
    }

    private Item getJson(String auth) {
        return new Item(auth, 1234);
    }

    private CompletionStage<String> getAuth(String auth) {
        return CompletableFuture.completedFuture(auth.replace("Basic ", ""));
    }

    private static class Item {

        final String name;
        final long id;

        @JsonCreator
        Item(@JsonProperty("name") String name,
             @JsonProperty("id") long id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }
    }
}
