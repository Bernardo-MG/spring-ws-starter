
package com.bernardomg.ws.test.response.config.controller;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class ReturnedObject {

    private final String name;

}
