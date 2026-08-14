package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.CIE10Data;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CIE10Repository extends ReactiveCrudRepository<CIE10Data, String>, ReactiveQueryByExampleExecutor<CIE10Data> {
}
