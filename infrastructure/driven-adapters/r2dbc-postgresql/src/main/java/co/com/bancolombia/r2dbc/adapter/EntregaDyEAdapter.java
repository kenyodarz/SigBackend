package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.gateways.EntregaDyEGateway;
import co.com.bancolombia.model.integrations.Items;
import co.com.bancolombia.r2dbc.data.EntregaDyEData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.EntregaDyERepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public class EntregaDyEAdapter extends ReactiveAdapterOperations<EntregaDyE, EntregaDyEData, String, EntregaDyERepository> implements EntregaDyEGateway {

    private final EmpleadoRepository empleadoRepository;
    private final DatabaseClient databaseClient;

    public EntregaDyEAdapter(EntregaDyERepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository, DatabaseClient databaseClient) {
        super(repository, mapper, d -> mapper.map(d, EntregaDyE.class));
        this.empleadoRepository = empleadoRepository;
        this.databaseClient = databaseClient;
    }

    private Mono<EntregaDyE> enrichEntregaDyE(EntregaDyEData d) {
        if (d == null) {
            return Mono.empty();
        }
        EntregaDyE entrega = mapper.map(d, EntregaDyE.class);

        Mono<Empleado> empMono = d.getEmpleadoCedula() != null
                ? empleadoRepository.findById(d.getEmpleadoCedula())
                .map(e -> mapper.map(e, Empleado.class))
                .defaultIfEmpty(Empleado.builder().build())
                : Mono.just(Empleado.builder().build());

        Mono<List<Items>> itemsMono = databaseClient.sql("SELECT i.* FROM items i JOIN entrega_dye_items edi ON i.id_items = edi.item_id WHERE edi.entrega_id = :entregaId")
                .bind("entregaId", d.getIdEntregaDyE())
                .map((row, metadata) -> Items.builder()
                        .idItems(row.get("id_items", String.class))
                        .nombre(row.get("nombre", String.class))
                        .marca(row.get("marca", String.class))
                        .talla(row.get("talla", String.class))
                        .categoria(row.get("categoria", String.class))
                        .serial(row.get("serial", String.class))
                        .color(row.get("color", String.class))
                        .build())
                .all()
                .collectList();

        return Mono.zip(empMono, itemsMono)
                .map(tuple -> {
                    if (tuple.getT1() != null && tuple.getT1().getCedula() != null) {
                        entrega.setEmpleado(tuple.getT1());
                    }
                    entrega.setItems(tuple.getT2());
                    return entrega;
                })
                .defaultIfEmpty(entrega);
    }

    @Override
    public Mono<EntregaDyE> findById(String id) {
        return repository.findById(id).flatMap(this::enrichEntregaDyE);
    }

    @Override
    public Flux<EntregaDyE> findAll() {
        return repository.findAll().flatMap(this::enrichEntregaDyE);
    }

    @Override
    public Mono<EntregaDyE> save(EntregaDyE entity) {
        EntregaDyEData data = toData(entity);
        return saveData(data).flatMap(this::enrichEntregaDyE);
    }

    @Override
    public Mono<EntregaDyE> agregarItems(String entregaId, List<Items> items) {
        if (items == null || items.isEmpty()) {
            return findById(entregaId);
        }
        return Flux.fromIterable(items)
                .flatMap(item -> {
                    String itemId = item.getIdItems();
                    if (itemId == null) {
                        return Mono.empty();
                    }
                    return databaseClient.sql("INSERT INTO entrega_dye_items (entrega_id, item_id) VALUES (:entregaId, :itemId) ON CONFLICT DO NOTHING")
                            .bind("entregaId", entregaId)
                            .bind("itemId", itemId)
                            .then();
                })
                .then(findById(entregaId));
    }

    @Override
    public Mono<EntregaDyE> eliminarItem(String entregaId, Items item) {
        if (item == null) {
            return findById(entregaId);
        }
        String itemId = item.getIdItems();
        if (itemId == null) {
            return findById(entregaId);
        }
        return databaseClient.sql("DELETE FROM entrega_dye_items WHERE entrega_id = :entregaId AND item_id = :itemId")
                .bind("entregaId", entregaId)
                .bind("itemId", itemId)
                .then(findById(entregaId));
    }

    @Override
    protected EntregaDyEData toData(EntregaDyE entity) {
        EntregaDyEData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEmpleado() != null && entity.getEmpleado().getCedula() != null) {
                data.setEmpleadoCedula(entity.getEmpleado().getCedula());
            }
        }
        return data;
    }

    @Override
    protected Mono<EntregaDyEData> saveData(EntregaDyEData data) {
        if (data != null) {
            if (data.getIdEntregaDyE() == null || data.getIdEntregaDyE().trim().isEmpty()) {
                data.setIdEntregaDyE("ENT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdEntregaDyE())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
