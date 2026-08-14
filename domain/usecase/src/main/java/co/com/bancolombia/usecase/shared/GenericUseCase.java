package co.com.bancolombia.usecase.shared;

import co.com.bancolombia.model.shared.gateways.CrudGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class GenericUseCase<T, ID> {

    private final CrudGateway<T, ID> gateway;

    public Mono<T> findById(ID id) {
        return gateway.findById(id);
    }

    public Flux<T> findAll() {
        return gateway.findAll();
    }

    public Mono<T> save(T entity) {
        return gateway.save(entity);
    }

    public Mono<Void> deleteById(ID id) {
        return gateway.deleteById(id);
    }
}
