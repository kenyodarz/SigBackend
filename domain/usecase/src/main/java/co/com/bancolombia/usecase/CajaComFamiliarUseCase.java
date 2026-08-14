package co.com.bancolombia.usecase;

import co.com.bancolombia.model.gateways.CajaComFamiliarGateway;
import co.com.bancolombia.model.integrations.CajaComFamiliar;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class CajaComFamiliarUseCase extends GenericUseCase<CajaComFamiliar, String> {

    public CajaComFamiliarUseCase(CajaComFamiliarGateway cajaComFamiliarGateway) {
        super(cajaComFamiliarGateway);
    }
}
