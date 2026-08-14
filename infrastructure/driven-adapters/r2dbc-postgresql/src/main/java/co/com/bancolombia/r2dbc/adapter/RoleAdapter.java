package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Role;
import co.com.bancolombia.model.gateways.RoleGateway;
import co.com.bancolombia.model.integrations.ERole;
import co.com.bancolombia.r2dbc.data.RoleData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.RoleRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RoleAdapter extends ReactiveAdapterOperations<Role, RoleData, String, RoleRepository> implements RoleGateway {

    public RoleAdapter(RoleRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Mono<Role> findByName(ERole name) {
        return repository.findByName(name).map(this::toEntity);
    }
}
