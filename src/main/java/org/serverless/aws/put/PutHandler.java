package org.serverless.aws.put;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import org.serverless.aws.utils.ErrorResponse;
import org.serverless.aws.utils.User;

import java.util.*;

public class PutHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    private static final List<User> users = new ArrayList<>(Collections.singletonList(
            new User(1, "Juan", "juan@example.com")
    ));

    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {

            String idParam = event.getPathParameters().get("id");
            Integer id = Integer.parseInt(idParam);


            User user = users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


            PutInput input = gson.fromJson(event.getBody(), PutInput.class);
            user.setNombre(input.getNombre());
            user.setEmail(input.getEmail());


            PutOutput output = new PutOutput(id, user.getNombre(), user.getEmail(), "Actualizado");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(gson.toJson(output));

        } catch (NumberFormatException e) {
            return ErrorResponse.build(400, "ID inválido");
        } catch (RuntimeException e) {
            return ErrorResponse.build(404, e.getMessage());
        } catch (Exception e) {
            return ErrorResponse.build(500, "Error interno");
        }
    }


}
