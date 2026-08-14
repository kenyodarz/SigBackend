package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.Role;
import co.com.bancolombia.model.integrations.ERole;
import co.com.bancolombia.model.shared.gateways.CrudGateway;
import reactor.core.publisher.Mono;

public interface RoleGateway extends CrudGateway<Role, String> {
    Mono<Role> findByName(ERole name);
}
