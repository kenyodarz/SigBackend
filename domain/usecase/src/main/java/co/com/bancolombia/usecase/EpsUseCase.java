package co.com.bancolombia.usecase;

import co.com.bancolombia.model.gateways.EpsGateway;
import co.com.bancolombia.model.integrations.Eps;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class EpsUseCase extends GenericUseCase<Eps, String> {

    public EpsUseCase(EpsGateway epsGateway) {
        super(epsGateway);
    }
}
