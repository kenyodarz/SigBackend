package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.DocumentoData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DocumentoRepository extends ReactiveCrudRepository<DocumentoData, String>, ReactiveQueryByExampleExecutor<DocumentoData> {
    Flux<DocumentoData> findByEmpleadoCedula(String cedula);
}
