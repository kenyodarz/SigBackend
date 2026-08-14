package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.integrations.Eps;
import co.com.bancolombia.usecase.EpsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EpsHandler {

    private final EpsUseCase epsUseCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(epsUseCase.findAll(), Eps.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return epsUseCase.findById(id)
                .flatMap(eps -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(eps))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Eps.class)
                .flatMap(epsUseCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return epsUseCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
