package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.usecase.EntregaDyEUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EntregaDyEHandler {

    private final EntregaDyEUseCase useCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(useCase.findAll(), EntregaDyE.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.findById(id)
                .flatMap(entity -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(entity))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(EntregaDyE.class)
                .flatMap(useCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> agregarItems(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToFlux(Items.class)
                .collectList()
                .flatMap(items -> useCase.agregarItems(id, items))
                .flatMap(updated -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updated))
                .switchIfEmpty(useCase.findById(id).flatMap(e -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(e)));
    }

    public Mono<ServerResponse> eliminarItem(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Items.class)
                .flatMap(item -> useCase.eliminarItem(id, item))
                .flatMap(updated -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updated))
                .switchIfEmpty(useCase.findById(id).flatMap(e -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(e)));
    }
}
