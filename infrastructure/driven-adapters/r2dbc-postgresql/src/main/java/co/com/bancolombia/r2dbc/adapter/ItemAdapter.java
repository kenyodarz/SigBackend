package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.ItemGateway;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.r2dbc.data.ItemsData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ItemsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ItemAdapter extends ReactiveAdapterOperations<Items, ItemsData, String, ItemsRepository> implements ItemGateway {

    public ItemAdapter(ItemsRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Items.class));
    }
}
