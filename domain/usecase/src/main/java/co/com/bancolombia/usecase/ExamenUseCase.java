package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Examen;
import co.com.bancolombia.model.gateways.ExamenGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class ExamenUseCase extends GenericUseCase<Examen, String> {

    public ExamenUseCase(ExamenGateway examenGateway) {
        super(examenGateway);
    }
}
