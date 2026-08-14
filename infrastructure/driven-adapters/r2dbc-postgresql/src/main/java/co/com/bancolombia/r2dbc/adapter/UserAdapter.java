package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Role;
import co.com.bancolombia.model.User;
import co.com.bancolombia.model.gateways.UserGateway;
import co.com.bancolombia.model.integrations.ERole;
import co.com.bancolombia.r2dbc.data.UserData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.UserRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Repository
public class UserAdapter extends ReactiveAdapterOperations<User, UserData, String, UserRepository> implements UserGateway {

    private final DatabaseClient databaseClient;

    public UserAdapter(UserRepository repository, ObjectMapper mapper) {
        this(repository, mapper, null);
    }

    @Autowired
    public UserAdapter(UserRepository repository, ObjectMapper mapper, DatabaseClient databaseClient) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<User> save(User user) {
        return super.save(user)
                .flatMap(savedUser -> {
                    if (databaseClient == null || user.getRoles() == null || user.getRoles().isEmpty()) {
                        return Mono.just(savedUser);
                    }
                    return Flux.fromIterable(user.getRoles())
                            .flatMap(role -> databaseClient.sql("INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId) ON CONFLICT DO NOTHING")
                                    .bind("userId", savedUser.getId())
                                    .bind("roleId", role.getId())
                                    .fetch().rowsUpdated())
                            .then(loadUserRoles(savedUser));
                });
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(this::toEntity)
                .flatMap(this::loadUserRoles);
    }

    @Override
    public Mono<User> findById(String id) {
        return super.findById(id)
                .flatMap(this::loadUserRoles);
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private Mono<User> loadUserRoles(User user) {
        if (user == null) {
            return Mono.empty();
        }
        if (databaseClient == null) {
            return Mono.just(user);
        }
        return databaseClient.sql("SELECT r.id, r.name FROM roles r INNER JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = :userId")
                .bind("userId", user.getId())
                .map((row, metadata) -> {
                    String roleId = row.get("id", String.class);
                    String roleNameStr = row.get("name", String.class);
                    ERole eRole = ERole.valueOf(roleNameStr);
                    return Role.builder().id(roleId).name(eRole).build();
                })
                .all()
                .collect(Collectors.toSet())
                .map(roles -> {
                    user.setRoles(roles);
                    return user;
                })
                .onErrorReturn(user);
    }
}
