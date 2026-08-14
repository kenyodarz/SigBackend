package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.CapacitacionData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CapacitacionRepository extends ReactiveCrudRepository<CapacitacionData, String>, ReactiveQueryByExampleExecutor<CapacitacionData> {
}
