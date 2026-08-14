package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.model.shared.gateways.CrudGateway;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EntregaDyEGateway extends CrudGateway<EntregaDyE, String> {
    Mono<EntregaDyE> agregarItems(String entregaId, List<Items> items);
    Mono<EntregaDyE> eliminarItem(String entregaId, Items item);
}
