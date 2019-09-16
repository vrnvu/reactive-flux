package com.github.vrnvu.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class DTOHandler {

    private final DTOGenerator DTOGenerator;

    public DTOHandler(DTOGenerator DTOGenerator) {
        this.DTOGenerator = DTOGenerator;
    }

    public Mono<ServerResponse> share(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(this.DTOGenerator.fetch(), DTO.class);
    }
}
