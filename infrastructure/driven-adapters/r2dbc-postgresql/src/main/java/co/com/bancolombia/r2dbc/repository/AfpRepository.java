package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.AfpData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AfpRepository extends ReactiveCrudRepository<AfpData, String>, ReactiveQueryByExampleExecutor<AfpData> {
}
