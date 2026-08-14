package co.com.bancolombia.usecase;

import co.com.bancolombia.model.gateways.CIE10Gateway;
import co.com.bancolombia.model.integrations.CIE10;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class CIE10UseCase extends GenericUseCase<CIE10, String> {

    public CIE10UseCase(CIE10Gateway cie10Gateway) {
        super(cie10Gateway);
    }
}
