package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.shared.gateways.CrudGateway;
import reactor.core.publisher.Flux;

public interface DocumentoGateway extends CrudGateway<Documento, String> {
    Flux<Documento> findByEmpleadoCedula(String cedula);
}
