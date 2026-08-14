package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.EpsData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EpsRepository extends ReactiveCrudRepository<EpsData, String>, ReactiveQueryByExampleExecutor<EpsData> {
}
