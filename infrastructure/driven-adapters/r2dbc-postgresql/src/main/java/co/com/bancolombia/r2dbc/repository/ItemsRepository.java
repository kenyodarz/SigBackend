package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.data.ItemsData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemsRepository extends ReactiveCrudRepository<ItemsData, String>, ReactiveQueryByExampleExecutor<ItemsData> {
}
