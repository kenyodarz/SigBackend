package co.com.bancolombia.usecase;

import co.com.bancolombia.model.User;
import co.com.bancolombia.model.gateways.UserGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;
import reactor.core.publisher.Mono;

public class UserUseCase extends GenericUseCase<User, String> {

    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        super(userGateway);
        this.userGateway = userGateway;
    }

    public Mono<User> findByUsername(String username) {
        return userGateway.findByUsername(username);
    }

    public Mono<Boolean> existsByUsername(String username) {
        return userGateway.existsByUsername(username);
    }

    public Mono<Boolean> existsByEmail(String email) {
        return userGateway.existsByEmail(email);
    }
}
