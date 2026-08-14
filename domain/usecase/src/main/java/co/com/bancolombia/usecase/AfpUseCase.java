package co.com.bancolombia.usecase;

import co.com.bancolombia.model.gateways.AfpGateway;
import co.com.bancolombia.model.integrations.Afp;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class AfpUseCase extends GenericUseCase<Afp, String> {

    public AfpUseCase(AfpGateway afpGateway) {
        super(afpGateway);
    }
}
