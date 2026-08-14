package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.EmpleadoGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class EmpleadoUseCase extends GenericUseCase<Empleado, String> {

    public EmpleadoUseCase(EmpleadoGateway empleadoGateway) {
        super(empleadoGateway);
    }
}
