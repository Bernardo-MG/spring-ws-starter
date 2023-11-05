
package com.bernardomg.ws.test.error.util.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ErrorTestObject {

    @NotNull
    private String name;

}
