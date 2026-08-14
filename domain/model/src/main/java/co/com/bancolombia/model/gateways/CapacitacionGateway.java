package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.shared.gateways.CrudGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CapacitacionGateway extends CrudGateway<Capacitacion, String> {
    Mono<Capacitacion> asignarEmpleados(String id, List<Empleado> empleados);
    Mono<Capacitacion> eliminarEmpleado(String id, Empleado empleado);
    Flux<Capacitacion> buscarPorEmpleado(String cedula);
}
