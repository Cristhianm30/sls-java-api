package org.serverless.aws.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.util.Map;

public class ErrorResponse {
    private static final Gson gson = new Gson();

    public static APIGatewayProxyResponseEvent build(int statusCode, String mensaje) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withBody(gson.toJson(Map.of("error", mensaje)));
    }
}
