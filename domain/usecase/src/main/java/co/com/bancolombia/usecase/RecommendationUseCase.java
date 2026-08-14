package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Recommendation;
import co.com.bancolombia.model.gateways.RecommendationGateway;
import co.com.bancolombia.usecase.shared.GenericUseCase;

public class RecommendationUseCase extends GenericUseCase<Recommendation, String> {

    public RecommendationUseCase(RecommendationGateway recommendationGateway) {
        super(recommendationGateway);
    }
}
