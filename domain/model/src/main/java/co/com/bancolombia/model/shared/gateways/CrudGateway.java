package co.com.bancolombia.model.shared.gateways;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudGateway<T, ID> {
    Mono<T> save(T entity);
    Mono<T> findById(ID id);
    Flux<T> findAll();
    Mono<Void> deleteById(ID id);
}
