package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.DocumentoData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DocumentoRepository extends ReactiveCrudRepository<DocumentoData, String>, ReactiveQueryByExampleExecutor<DocumentoData> {
}
