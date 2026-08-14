package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.User;
import co.com.bancolombia.model.gateways.UserGateway;
import co.com.bancolombia.r2dbc.data.UserData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.UserRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserAdapter extends ReactiveAdapterOperations<User, UserData, String, UserRepository> implements UserGateway {

    public UserAdapter(UserRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username).map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
