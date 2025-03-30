package org.serverless.aws.put;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutOutput {
    private Integer id;
    private String nombre;
    private String email;
    private String mensaje;
}
