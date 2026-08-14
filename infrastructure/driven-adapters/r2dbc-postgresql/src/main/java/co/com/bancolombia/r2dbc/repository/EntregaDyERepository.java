package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.EntregaDyEData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EntregaDyERepository extends ReactiveCrudRepository<EntregaDyEData, String>, ReactiveQueryByExampleExecutor<EntregaDyEData> {
}
