package co.com.bancolombia.usecase;

import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.gateways.EntregaDyEGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class EntregaDyEUseCase extends GenericUseCase<EntregaDyE, String> {

    public EntregaDyEUseCase(EntregaDyEGateway entregaDyEGateway) {
        super(entregaDyEGateway);
    }
}
