package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.ExamenData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExamenRepository extends ReactiveCrudRepository<ExamenData, String>, ReactiveQueryByExampleExecutor<ExamenData> {
}
