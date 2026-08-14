package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Recommendation;
import co.com.bancolombia.model.gateways.RecommendationGateway;
import co.com.bancolombia.r2dbc.data.RecommendationData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.RecommendationRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendationAdapter extends ReactiveAdapterOperations<Recommendation, RecommendationData, String, RecommendationRepository> implements RecommendationGateway {

    public RecommendationAdapter(RecommendationRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Recommendation.class));
    }
}
