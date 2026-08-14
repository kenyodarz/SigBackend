package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.gateways.DocumentoGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class DocumentoUseCase extends GenericUseCase<Documento, String> {

    public DocumentoUseCase(DocumentoGateway documentoGateway) {
        super(documentoGateway);
    }
}
