package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.usecase.CapacitacionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CapacitacionHandler {

    private final CapacitacionUseCase useCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(useCase.findAll(), Capacitacion.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.findById(id)
                .flatMap(entity -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(entity))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Capacitacion.class)
                .flatMap(useCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> asignarEmpleados(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToFlux(Empleado.class)
                .collectList()
                .flatMap(empleados -> useCase.asignarEmpleados(id, empleados))
                .flatMap(updated -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updated));
    }

    public Mono<ServerResponse> eliminarEmpleado(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Empleado.class)
                .flatMap(empleado -> useCase.eliminarEmpleado(id, empleado))
                .flatMap(updated -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updated));
    }

    public Mono<ServerResponse> findByEmpleado(ServerRequest request) {
        String cedula = request.pathVariable("cedula");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(useCase.buscarPorEmpleado(cedula), Capacitacion.class);
    }
}
