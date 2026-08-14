package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.gateways.ContratoGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class ContratoUseCase extends GenericUseCase<Contrato, String> {

    public ContratoUseCase(ContratoGateway contratoGateway) {
        super(contratoGateway);
    }
}
