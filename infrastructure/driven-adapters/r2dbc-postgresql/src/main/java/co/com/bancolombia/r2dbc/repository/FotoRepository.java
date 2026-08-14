package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.FotoData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FotoRepository extends ReactiveCrudRepository<FotoData, String>, ReactiveQueryByExampleExecutor<FotoData> {
}
