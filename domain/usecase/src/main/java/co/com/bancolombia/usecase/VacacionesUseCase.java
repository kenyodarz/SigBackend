package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Vacaciones;
import co.com.bancolombia.model.gateways.VacacionesGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class VacacionesUseCase extends GenericUseCase<Vacaciones, String> {

    public VacacionesUseCase(VacacionesGateway vacacionesGateway) {
        super(vacacionesGateway);
    }
}
