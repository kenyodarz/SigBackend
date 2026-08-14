package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.ItemGateway;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.r2dbc.data.ItemsData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ItemsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ItemAdapter extends ReactiveAdapterOperations<Items, ItemsData, String, ItemsRepository> implements ItemGateway {

    public ItemAdapter(ItemsRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Items.class));
    }

    @Override
    protected Mono<ItemsData> saveData(ItemsData data) {
        if (data != null) {
            if (data.getIdItems() == null || data.getIdItems().trim().isEmpty()) {
                data.setIdItems("ITM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdItems())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
