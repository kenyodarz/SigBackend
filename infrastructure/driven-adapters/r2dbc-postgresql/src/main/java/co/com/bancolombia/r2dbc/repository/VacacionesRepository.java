package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.VacacionesData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VacacionesRepository extends ReactiveCrudRepository<VacacionesData, String>, ReactiveQueryByExampleExecutor<VacacionesData> {
}
