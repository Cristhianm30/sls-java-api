package org.serverless.aws.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


public class ErrorResponse {

    public static APIGatewayProxyResponseEvent build(int statusCode, String mensaje) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withBody("{\"error\": \"" + mensaje + "\"}");
    }
}
