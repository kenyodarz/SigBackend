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
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperations({
            @RouterOperation(path = "/api/auth/signin", method = RequestMethod.POST, beanClass = AuthHandler.class, beanMethod = "signin"),
            @RouterOperation(path = "/api/auth/signup", method = RequestMethod.POST, beanClass = AuthHandler.class, beanMethod = "signup"),

            @RouterOperation(path = "/api/empleados", method = RequestMethod.GET, beanClass = EmpleadoHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/empleados/{id}", method = RequestMethod.GET, beanClass = EmpleadoHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/empleados", method = RequestMethod.POST, beanClass = EmpleadoHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/empleados", method = RequestMethod.PUT, beanClass = EmpleadoHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/empleados/{id}", method = RequestMethod.DELETE, beanClass = EmpleadoHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.GET, beanClass = CapacitacionHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/capacitaciones/{id}", method = RequestMethod.GET, beanClass = CapacitacionHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.POST, beanClass = CapacitacionHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.PUT, beanClass = CapacitacionHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/capacitaciones/{id}", method = RequestMethod.DELETE, beanClass = CapacitacionHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/contratos", method = RequestMethod.GET, beanClass = ContratoHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/contratos/{id}", method = RequestMethod.GET, beanClass = ContratoHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/contratos", method = RequestMethod.POST, beanClass = ContratoHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/contratos", method = RequestMethod.PUT, beanClass = ContratoHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/contratos/{id}", method = RequestMethod.DELETE, beanClass = ContratoHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/documentos", method = RequestMethod.GET, beanClass = DocumentoHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/documentos/{id}", method = RequestMethod.GET, beanClass = DocumentoHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/documentos", method = RequestMethod.POST, beanClass = DocumentoHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/documentos", method = RequestMethod.PUT, beanClass = DocumentoHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/documentos/{id}", method = RequestMethod.DELETE, beanClass = DocumentoHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.GET, beanClass = EntregaDyEHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/entrega-dye/{id}", method = RequestMethod.GET, beanClass = EntregaDyEHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.POST, beanClass = EntregaDyEHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.PUT, beanClass = EntregaDyEHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/entrega-dye/{id}", method = RequestMethod.DELETE, beanClass = EntregaDyEHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/examenes", method = RequestMethod.GET, beanClass = ExamenHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/examenes/{id}", method = RequestMethod.GET, beanClass = ExamenHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/examenes", method = RequestMethod.POST, beanClass = ExamenHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/examenes", method = RequestMethod.PUT, beanClass = ExamenHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/examenes/{id}", method = RequestMethod.DELETE, beanClass = ExamenHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.GET, beanClass = IncapacidadHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/incapacidades/{id}", method = RequestMethod.GET, beanClass = IncapacidadHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.POST, beanClass = IncapacidadHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.PUT, beanClass = IncapacidadHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/incapacidades/{id}", method = RequestMethod.DELETE, beanClass = IncapacidadHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.GET, beanClass = RecommendationHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/recomendaciones/{id}", method = RequestMethod.GET, beanClass = RecommendationHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.POST, beanClass = RecommendationHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.PUT, beanClass = RecommendationHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/recomendaciones/{id}", method = RequestMethod.DELETE, beanClass = RecommendationHandler.class, beanMethod = "delete"),

            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.GET, beanClass = VacacionesHandler.class, beanMethod = "findAll"),
            @RouterOperation(path = "/api/vacaciones/{id}", method = RequestMethod.GET, beanClass = VacacionesHandler.class, beanMethod = "findById"),
            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.POST, beanClass = VacacionesHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.PUT, beanClass = VacacionesHandler.class, beanMethod = "save"),
            @RouterOperation(path = "/api/vacaciones/{id}", method = RequestMethod.DELETE, beanClass = VacacionesHandler.class, beanMethod = "delete")
    })
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
