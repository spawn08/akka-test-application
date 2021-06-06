package com.app.utils;

import com.app.model.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public final class Utils {

    public static Response getResponse(String token) {
        return new Response(token, 1234);
    }

    public static CompletionStage<String> getAuth(String auth) {
        return CompletableFuture.completedFuture(auth.replace("Basic ", ""));
    }
}
