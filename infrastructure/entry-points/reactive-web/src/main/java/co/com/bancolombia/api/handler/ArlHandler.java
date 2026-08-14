package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.integrations.Arl;
import co.com.bancolombia.usecase.ArlUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ArlHandler {

    private final ArlUseCase arlUseCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arlUseCase.findAll(), Arl.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return arlUseCase.findById(id)
                .flatMap(arl -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(arl))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Arl.class)
                .flatMap(arlUseCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return arlUseCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
