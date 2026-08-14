package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.CapacitacionGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class CapacitacionUseCase extends GenericUseCase<Capacitacion, String> {

    private final CapacitacionGateway capacitacionGateway;

    public CapacitacionUseCase(CapacitacionGateway capacitacionGateway) {
        super(capacitacionGateway);
        this.capacitacionGateway = capacitacionGateway;
    }

    public Mono<Capacitacion> asignarEmpleados(String id, List<Empleado> empleados) {
        return capacitacionGateway.asignarEmpleados(id, empleados);
    }

    public Mono<Capacitacion> eliminarEmpleado(String id, Empleado empleado) {
        return capacitacionGateway.eliminarEmpleado(id, empleado);
    }

    public Flux<Capacitacion> buscarPorEmpleado(String cedula) {
        return capacitacionGateway.buscarPorEmpleado(cedula);
    }
}
