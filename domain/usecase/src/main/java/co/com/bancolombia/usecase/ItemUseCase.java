package co.com.bancolombia.usecase;

import co.com.bancolombia.model.gateways.ItemGateway;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class ItemUseCase extends GenericUseCase<Items, String> {

    public ItemUseCase(ItemGateway itemGateway) {
        super(itemGateway);
    }
}
