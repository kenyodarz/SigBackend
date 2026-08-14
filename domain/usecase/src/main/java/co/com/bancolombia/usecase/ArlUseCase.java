package co.com.bancolombia.usecase;

import co.com.bancolombia.model.gateways.ArlGateway;
import co.com.bancolombia.model.integrations.Arl;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class ArlUseCase extends GenericUseCase<Arl, String> {

    public ArlUseCase(ArlGateway arlGateway) {
        super(arlGateway);
    }
}
