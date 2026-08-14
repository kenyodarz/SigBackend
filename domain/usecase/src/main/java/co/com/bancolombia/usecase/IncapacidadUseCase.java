package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Incapacidad;
import co.com.bancolombia.model.gateways.IncapacidadGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class IncapacidadUseCase extends GenericUseCase<Incapacidad, String> {

    public IncapacidadUseCase(IncapacidadGateway incapacidadGateway) {
        super(incapacidadGateway);
    }
}
