package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Role;
import co.com.bancolombia.model.gateways.RoleGateway;
import co.com.bancolombia.model.integrations.ERole;
import co.com.bancolombia.usecase.shared.GenericUseCase;
import reactor.core.publisher.Mono;

public class RoleUseCase extends GenericUseCase<Role, String> {

    private final RoleGateway roleGateway;

    public RoleUseCase(RoleGateway roleGateway) {
        super(roleGateway);
        this.roleGateway = roleGateway;
    }

    public Mono<Role> findByName(ERole name) {
        return roleGateway.findByName(name);
    }
}
