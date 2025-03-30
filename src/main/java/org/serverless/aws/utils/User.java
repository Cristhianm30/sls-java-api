package org.serverless.aws.utils;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import lombok.Data;

@Data
@DynamoDbBean
public class User {
    private Integer id;
    private String nombre;
    private String email;

    @DynamoDbPartitionKey
    public Integer getId() {
        return id;
    }
}