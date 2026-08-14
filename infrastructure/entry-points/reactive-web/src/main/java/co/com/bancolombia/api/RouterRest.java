package co.com.bancolombia.api;

import co.com.bancolombia.api.handler.AuthHandler;
import co.com.bancolombia.api.handler.CapacitacionHandler;
import co.com.bancolombia.api.handler.ContratoHandler;
import co.com.bancolombia.api.handler.DocumentoHandler;
import co.com.bancolombia.api.handler.EmpleadoHandler;
import co.com.bancolombia.api.handler.EntregaDyEHandler;
import co.com.bancolombia.api.handler.ExamenHandler;
import co.com.bancolombia.api.handler.IncapacidadHandler;
import co.com.bancolombia.api.handler.RecommendationHandler;
import co.com.bancolombia.api.handler.VacacionesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            AuthHandler authHandler,
            EmpleadoHandler empleadoHandler,
            CapacitacionHandler capacitacionHandler,
            ContratoHandler contratoHandler,
            DocumentoHandler documentoHandler,
            EntregaDyEHandler entregaDyEHandler,
            ExamenHandler examenHandler,
            IncapacidadHandler incapacidadHandler,
            RecommendationHandler recommendationHandler,
            VacacionesHandler vacacionesHandler
    ) {
        return route(POST("/api/auth/signin"), authHandler::signin)
                .andRoute(POST("/api/auth/signup"), authHandler::signup)

                .andRoute(GET("/api/empleados"), empleadoHandler::findAll)
                .andRoute(GET("/api/empleados/{id}"), empleadoHandler::findById)
                .andRoute(POST("/api/empleados"), empleadoHandler::save)
                .andRoute(PUT("/api/empleados"), empleadoHandler::save)
                .andRoute(DELETE("/api/empleados/{id}"), empleadoHandler::delete)

                .andRoute(GET("/api/capacitaciones"), capacitacionHandler::findAll)
                .andRoute(GET("/api/capacitaciones/{id}"), capacitacionHandler::findById)
                .andRoute(POST("/api/capacitaciones"), capacitacionHandler::save)
                .andRoute(PUT("/api/capacitaciones"), capacitacionHandler::save)
                .andRoute(DELETE("/api/capacitaciones/{id}"), capacitacionHandler::delete)

                .andRoute(GET("/api/contratos"), contratoHandler::findAll)
                .andRoute(GET("/api/contratos/{id}"), contratoHandler::findById)
                .andRoute(POST("/api/contratos"), contratoHandler::save)
                .andRoute(PUT("/api/contratos"), contratoHandler::save)
                .andRoute(DELETE("/api/contratos/{id}"), contratoHandler::delete)

                .andRoute(GET("/api/documentos"), documentoHandler::findAll)
                .andRoute(GET("/api/documentos/{id}"), documentoHandler::findById)
                .andRoute(POST("/api/documentos"), documentoHandler::save)
                .andRoute(PUT("/api/documentos"), documentoHandler::save)
                .andRoute(DELETE("/api/documentos/{id}"), documentoHandler::delete)

                .andRoute(GET("/api/entrega-dye"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entrega-dye/{id}"), entregaDyEHandler::findById)
                .andRoute(POST("/api/entrega-dye"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entrega-dye"), entregaDyEHandler::save)
                .andRoute(DELETE("/api/entrega-dye/{id}"), entregaDyEHandler::delete)

                .andRoute(GET("/api/examenes"), examenHandler::findAll)
                .andRoute(GET("/api/examenes/{id}"), examenHandler::findById)
                .andRoute(POST("/api/examenes"), examenHandler::save)
                .andRoute(PUT("/api/examenes"), examenHandler::save)
                .andRoute(DELETE("/api/examenes/{id}"), examenHandler::delete)

                .andRoute(GET("/api/incapacidades"), incapacidadHandler::findAll)
                .andRoute(GET("/api/incapacidades/{id}"), incapacidadHandler::findById)
                .andRoute(POST("/api/incapacidades"), incapacidadHandler::save)
                .andRoute(PUT("/api/incapacidades"), incapacidadHandler::save)
                .andRoute(DELETE("/api/incapacidades/{id}"), incapacidadHandler::delete)

                .andRoute(GET("/api/recomendaciones"), recommendationHandler::findAll)
                .andRoute(GET("/api/recomendaciones/{id}"), recommendationHandler::findById)
                .andRoute(POST("/api/recomendaciones"), recommendationHandler::save)
                .andRoute(PUT("/api/recomendaciones"), recommendationHandler::save)
                .andRoute(DELETE("/api/recomendaciones/{id}"), recommendationHandler::delete)

                .andRoute(GET("/api/vacaciones"), vacacionesHandler::findAll)
                .andRoute(GET("/api/vacaciones/{id}"), vacacionesHandler::findById)
                .andRoute(POST("/api/vacaciones"), vacacionesHandler::save)
                .andRoute(PUT("/api/vacaciones"), vacacionesHandler::save)
                .andRoute(DELETE("/api/vacaciones/{id}"), vacacionesHandler::delete);
    }
}
