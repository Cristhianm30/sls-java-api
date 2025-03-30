package org.serverless.aws.delete;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import org.serverless.aws.utils.ErrorResponse;
import org.serverless.aws.utils.User;

import java.util.*;

public class DeleteHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    private static final List<User> users = new ArrayList<>(Collections.singletonList(
            new User(1, "Juan", "juan@example.com")
    ));

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {

            String idParam = event.getPathParameters().get("id");
            Integer id = Integer.parseInt(idParam);


            boolean removed = users.removeIf(u -> u.getId().equals(id));
            if (!removed) {
                throw new RuntimeException("Usuario no encontrado");
            }


            DeleteOutput output = new DeleteOutput("Usuario eliminado");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(new Gson().toJson(output));

        } catch (NumberFormatException e) {
            return ErrorResponse.build(400, "ID inválido");
        } catch (RuntimeException e) {
            return ErrorResponse.build(404, e.getMessage());
        }
    }

}