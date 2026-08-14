package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.User;
import co.com.bancolombia.model.shared.gateways.CrudGateway;
import reactor.core.publisher.Mono;

public interface UserGateway extends CrudGateway<User, String> {
    Mono<User> findByUsername(String username);
    Mono<Boolean> existsByUsername(String username);
    Mono<Boolean> existsByEmail(String email);
}
