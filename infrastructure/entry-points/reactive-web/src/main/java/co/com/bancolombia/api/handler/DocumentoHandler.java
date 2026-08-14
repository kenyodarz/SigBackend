package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.usecase.DocumentoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DocumentoHandler {

    private final DocumentoUseCase useCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(useCase.findAll(), Documento.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.findById(id)
                .flatMap(entity -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(entity))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Documento.class)
                .flatMap(useCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
