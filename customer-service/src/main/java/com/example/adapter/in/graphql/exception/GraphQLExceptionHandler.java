package com.example.adapter.in.graphql.exception;

import com.example.core.domain.exception.CustomerNotFoundException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler(CustomerNotFoundException.class)
    public GraphQLError handleCustomerNotFound(CustomerNotFoundException e, DataFetchingEnvironment env) {
        return GraphQLError.newError()
                .message(e.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }
}
