package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/JankenAPI". 
     */
    @FunctionName("JankenAPI")
    public HttpResponseMessage run(
    @HttpTrigger(
        name = "req",
        methods = {HttpMethod.POST},
        authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<String>> request,
    final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse move parameter
        final String move = request.getBody().orElse(null);

        if (move == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Please pass a valid move in the request body: rock, scissors, or paper.")
                .header("Content-Type", "text/plain")
                .build();
        } else {
            String result;
            switch (move) {
                case "rock":
                    result = "scissors";
                    break;
                case "scissors":
                    result = "paper";
                    break;
                case "paper":
                    result = "rock";
                    break;
                default:
                    result = "Invalid move";
                    break;
            }

            return request.createResponseBuilder(HttpStatus.OK)
                .body("\"" + result + "\"")
                .header("Content-Type", "application/json")
                .build();
        }
    }
}