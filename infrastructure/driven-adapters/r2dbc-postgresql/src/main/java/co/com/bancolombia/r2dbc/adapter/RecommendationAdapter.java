package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.Examen;
import co.com.bancolombia.model.Recommendation;
import co.com.bancolombia.model.gateways.RecommendationGateway;
import co.com.bancolombia.r2dbc.data.RecommendationData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ContratoRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.ExamenRepository;
import co.com.bancolombia.r2dbc.repository.RecommendationRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class RecommendationAdapter extends ReactiveAdapterOperations<Recommendation, RecommendationData, String, RecommendationRepository> implements RecommendationGateway {

    private final ExamenRepository examenRepository;
    private final ContratoRepository contratoRepository;
    private final EmpleadoRepository empleadoRepository;

    public RecommendationAdapter(RecommendationRepository repository, ObjectMapper mapper, ExamenRepository examenRepository, ContratoRepository contratoRepository, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, Recommendation.class));
        this.examenRepository = examenRepository;
        this.contratoRepository = contratoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<Recommendation> enrichRecommendation(RecommendationData d) {
        if (d == null) {
            return Mono.empty();
        }
        Recommendation rec = mapper.map(d, Recommendation.class);
        if (d.getExamenId() != null) {
            return examenRepository.findById(d.getExamenId())
                    .flatMap(exData -> {
                        Examen examen = mapper.map(exData, Examen.class);
                        if (exData.getContratoId() != null) {
                            return contratoRepository.findById(exData.getContratoId())
                                    .flatMap(cData -> {
                                        Contrato contrato = mapper.map(cData, Contrato.class);
                                        if (cData.getEmpleadoCedula() != null) {
                                            return empleadoRepository.findById(cData.getEmpleadoCedula())
                                                    .map(e -> mapper.map(e, Empleado.class))
                                                    .map(emp -> {
                                                        contrato.setEmpleado(emp);
                                                        examen.setContrato(contrato);
                                                        rec.setExamen(examen);
                                                        return rec;
                                                    })
                                                    .defaultIfEmpty(rec);
                                        }
                                        examen.setContrato(contrato);
                                        rec.setExamen(examen);
                                        return Mono.just(rec);
                                    })
                                    .defaultIfEmpty(rec);
                        }
                        rec.setExamen(examen);
                        return Mono.just(rec);
                    })
                    .defaultIfEmpty(rec);
        }
        return Mono.just(rec);
    }

    @Override
    public Mono<Recommendation> findById(String id) {
        return repository.findById(id).flatMap(this::enrichRecommendation);
    }

    @Override
    public Flux<Recommendation> findAll() {
        return repository.findAll().flatMap(this::enrichRecommendation);
    }

    @Override
    public Mono<Recommendation> save(Recommendation entity) {
        RecommendationData data = toData(entity);
        return saveData(data).flatMap(this::enrichRecommendation);
    }

    @Override
    protected RecommendationData toData(Recommendation entity) {
        RecommendationData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getExamen() != null && entity.getExamen().getIdExamen() != null) {
                data.setExamenId(entity.getExamen().getIdExamen());
            }
        }
        return data;
    }

    @Override
    protected Mono<RecommendationData> saveData(RecommendationData data) {
        if (data != null) {
            if (data.getCreateAt() == null) {
                data.setCreateAt(LocalDateTime.now());
            }
            if (data.getIdRecomendaciones() == null || data.getIdRecomendaciones().trim().isEmpty()) {
                data.setIdRecomendaciones("REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdRecomendaciones())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
