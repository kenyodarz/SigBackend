package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Foto;
import co.com.bancolombia.model.gateways.FotoGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class FotoUseCase extends GenericUseCase<Foto, String> {

    public FotoUseCase(FotoGateway fotoGateway) {
        super(fotoGateway);
    }
}
