package co.com.bancolombia.usecase;

import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.gateways.EntregaDyEGateway;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.usecase.shared.GenericUseCase;
import reactor.core.publisher.Mono;

import java.util.List;

public class EntregaDyEUseCase extends GenericUseCase<EntregaDyE, String> {

    private final EntregaDyEGateway entregaDyEGateway;

    public EntregaDyEUseCase(EntregaDyEGateway entregaDyEGateway) {
        super(entregaDyEGateway);
        this.entregaDyEGateway = entregaDyEGateway;
    }

    public Mono<EntregaDyE> agregarItems(String entregaId, List<Items> items) {
        return entregaDyEGateway.agregarItems(entregaId, items);
    }

    public Mono<EntregaDyE> eliminarItem(String entregaId, Items item) {
        return entregaDyEGateway.eliminarItem(entregaId, item);
    }
}
