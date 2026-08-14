package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.IncapacidadData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IncapacidadRepository extends ReactiveCrudRepository<IncapacidadData, String>, ReactiveQueryByExampleExecutor<IncapacidadData> {
}
