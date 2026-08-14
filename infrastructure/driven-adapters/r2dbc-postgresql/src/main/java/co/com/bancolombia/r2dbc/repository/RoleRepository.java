package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.model.integrations.ERole;
import co.com.bancolombia.r2dbc.data.RoleData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveCrudRepository<RoleData, String>, ReactiveQueryByExampleExecutor<RoleData> {
    Mono<RoleData> findByName(ERole name);
}
