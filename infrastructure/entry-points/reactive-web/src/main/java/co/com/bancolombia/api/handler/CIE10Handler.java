package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.integrations.Cie10;
import co.com.bancolombia.usecase.CIE10UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CIE10Handler {

    private final CIE10UseCase cie10UseCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cie10UseCase.findAll(), Cie10.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return cie10UseCase.findById(id)
                .flatMap(cie -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(cie))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Cie10.class)
                .flatMap(cie10UseCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return cie10UseCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
