package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.usecase.EmpleadoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmpleadoHandler {

    private final EmpleadoUseCase empleadoUseCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(empleadoUseCase.findAll(), Empleado.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return empleadoUseCase.findById(id)
                .flatMap(empleado -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(empleado))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Empleado.class)
                .flatMap(empleadoUseCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return empleadoUseCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
