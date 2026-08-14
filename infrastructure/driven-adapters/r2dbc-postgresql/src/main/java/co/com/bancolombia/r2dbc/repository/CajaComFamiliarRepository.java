package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.CajaComFamiliarData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CajaComFamiliarRepository extends ReactiveCrudRepository<CajaComFamiliarData, String>, ReactiveQueryByExampleExecutor<CajaComFamiliarData> {
}
