package com.app.utils;

import com.app.model.Response;

public final class Utils {

    public static final String AUTH_TOKEN = "Y2xpY2tzdHJlYW1hZ2c6b2ZRVUlEZyE2YTkmKmslQ1MlZEl3TURCKllGdTFkaTIwYVd4WXNJWA==";

    public static Response getResponse(String status) {
        return new Response(status);
    }

    public static String getAuth(String auth) {
        return auth.replace("Basic ", "");
    }
}
