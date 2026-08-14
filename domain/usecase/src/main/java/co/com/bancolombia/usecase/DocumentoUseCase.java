package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.gateways.DocumentoGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;
import reactor.core.publisher.Flux;

public class DocumentoUseCase extends GenericUseCase<Documento, String> {

    private final DocumentoGateway documentoGateway;

    public DocumentoUseCase(DocumentoGateway documentoGateway) {
        super(documentoGateway);
        this.documentoGateway = documentoGateway;
    }

    public Flux<Documento> findByEmpleadoCedula(String cedula) {
        return documentoGateway.findByEmpleadoCedula(cedula);
    }
}
