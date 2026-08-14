package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.integrations.CajaComFamiliar;
import co.com.bancolombia.usecase.CajaComFamiliarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CajaComFamiliarHandler {

    private final CajaComFamiliarUseCase cajaComFamiliarUseCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cajaComFamiliarUseCase.findAll(), CajaComFamiliar.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return cajaComFamiliarUseCase.findById(id)
                .flatMap(caja -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(caja))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(CajaComFamiliar.class)
                .flatMap(cajaComFamiliarUseCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return cajaComFamiliarUseCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
