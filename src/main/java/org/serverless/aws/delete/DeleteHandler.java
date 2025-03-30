package org.serverless.aws.delete;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.serverless.aws.utils.ErrorResponse;
import org.serverless.aws.utils.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class DeleteHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final DynamoDbClient dynamoClient = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .build();

    private static final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoClient)
            .build();

    private static final DynamoDbTable<User> userTable = enhancedClient.table(
            "sls-users",
            TableSchema.fromBean(User.class)
    );

    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {
            Integer id = Integer.parseInt(event.getPathParameters().get("id"));

            User deletedUser = userTable.deleteItem(Key.builder().partitionValue(id).build());
            if (deletedUser == null) {
                return ErrorResponse.build(404, "Usuario no encontrado");
            }

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(gson.toJson(new DeleteOutput("Usuario eliminado")));

        } catch (NumberFormatException e) {
            return ErrorResponse.build(400, "ID inválido");
        } catch (Exception e) {
            return ErrorResponse.build(500, "Error interno: " + e.getMessage());
        }
    }
}