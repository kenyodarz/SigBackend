package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.gateways.CapacitacionGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class CapacitacionUseCase extends GenericUseCase<Capacitacion, String> {

    public CapacitacionUseCase(CapacitacionGateway capacitacionGateway) {
        super(capacitacionGateway);
    }
}
