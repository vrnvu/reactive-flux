package com.github.vrnvu.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class DTORouter {
    @Bean
    public RouterFunction<ServerResponse> dtoRoute(DTOHandler dtoHandler) {
        return RouterFunctions
                .route(GET("/dtos").and(accept(MediaType.APPLICATION_STREAM_JSON)), dtoHandler::share);
    }
}
