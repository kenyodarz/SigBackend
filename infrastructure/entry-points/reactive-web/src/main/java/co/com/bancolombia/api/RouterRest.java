package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.JwtResponse;
import co.com.bancolombia.api.dto.LoginRequest;
import co.com.bancolombia.api.dto.MessageResponse;
import co.com.bancolombia.api.dto.SignupRequest;
import co.com.bancolombia.api.handler.AfpHandler;
import co.com.bancolombia.api.handler.ArlHandler;
import co.com.bancolombia.api.handler.AuthHandler;
import co.com.bancolombia.api.handler.CIE10Handler;
import co.com.bancolombia.api.handler.CajaComFamiliarHandler;
import co.com.bancolombia.api.handler.CapacitacionHandler;
import co.com.bancolombia.api.handler.ContratoHandler;
import co.com.bancolombia.api.handler.DocumentoHandler;
import co.com.bancolombia.api.handler.EmpleadoHandler;
import co.com.bancolombia.api.handler.EntregaDyEHandler;
import co.com.bancolombia.api.handler.EpsHandler;
import co.com.bancolombia.api.handler.ExamenHandler;
import co.com.bancolombia.api.handler.IncapacidadHandler;
import co.com.bancolombia.api.handler.ItemHandler;
import co.com.bancolombia.api.handler.RecommendationHandler;
import co.com.bancolombia.api.handler.VacacionesHandler;
import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.Examen;
import co.com.bancolombia.model.Incapacidad;
import co.com.bancolombia.model.Recommendation;
import co.com.bancolombia.model.Vacaciones;
import co.com.bancolombia.model.integrations.Afp;
import co.com.bancolombia.model.integrations.Arl;
import co.com.bancolombia.model.integrations.CIE10;
import co.com.bancolombia.model.integrations.CajaComFamiliar;
import co.com.bancolombia.model.integrations.Eps;
import co.com.bancolombia.model.integrations.Items;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
            VacacionesHandler vacacionesHandler,
            EpsHandler epsHandler,
            ArlHandler arlHandler,
            AfpHandler afpHandler,
            CajaComFamiliarHandler cajaComFamiliarHandler,
            ItemHandler itemHandler,
            CIE10Handler cie10Handler
    ) {
        return route(POST("/api/auth/signin"), authHandler::signin)
                .andRoute(POST("/api/auth/signup"), authHandler::signup)

                .andRoute(GET("/api/empleados"), empleadoHandler::findAll)
                .andRoute(GET("/api/empleados/all"), empleadoHandler::findAll)
                .andRoute(GET("/api/empleados/{id}"), empleadoHandler::findById)
                .andRoute(POST("/api/empleados"), empleadoHandler::save)
                .andRoute(PUT("/api/empleados"), empleadoHandler::save)
                .andRoute(DELETE("/api/empleados/{id}"), empleadoHandler::delete)

                .andRoute(GET("/api/eps"), epsHandler::findAll)
                .andRoute(GET("/api/eps/all"), epsHandler::findAll)
                .andRoute(GET("/api/eps/{id}"), epsHandler::findById)
                .andRoute(POST("/api/eps"), epsHandler::save)
                .andRoute(PUT("/api/eps"), epsHandler::save)
                .andRoute(DELETE("/api/eps/{id}"), epsHandler::delete)

                .andRoute(GET("/api/arl"), arlHandler::findAll)
                .andRoute(GET("/api/arl/all"), arlHandler::findAll)
                .andRoute(GET("/api/arl/{id}"), arlHandler::findById)
                .andRoute(POST("/api/arl"), arlHandler::save)
                .andRoute(PUT("/api/arl"), arlHandler::save)
                .andRoute(DELETE("/api/arl/{id}"), arlHandler::delete)

                .andRoute(GET("/api/afp"), afpHandler::findAll)
                .andRoute(GET("/api/afp/all"), afpHandler::findAll)
                .andRoute(GET("/api/afp/{id}"), afpHandler::findById)
                .andRoute(POST("/api/afp"), afpHandler::save)
                .andRoute(PUT("/api/afp"), afpHandler::save)
                .andRoute(DELETE("/api/afp/{id}"), afpHandler::delete)

                .andRoute(GET("/api/caja"), cajaComFamiliarHandler::findAll)
                .andRoute(GET("/api/caja/all"), cajaComFamiliarHandler::findAll)
                .andRoute(GET("/api/caja/{id}"), cajaComFamiliarHandler::findById)
                .andRoute(POST("/api/caja"), cajaComFamiliarHandler::save)
                .andRoute(PUT("/api/caja"), cajaComFamiliarHandler::save)
                .andRoute(DELETE("/api/caja/{id}"), cajaComFamiliarHandler::delete)

                .andRoute(GET("/api/items"), itemHandler::findAll)
                .andRoute(GET("/api/items/all"), itemHandler::findAll)
                .andRoute(GET("/api/items/{id}"), itemHandler::findById)
                .andRoute(POST("/api/items"), itemHandler::save)
                .andRoute(PUT("/api/items"), itemHandler::save)
                .andRoute(DELETE("/api/items/{id}"), itemHandler::delete)

                .andRoute(GET("/api/cie10"), cie10Handler::findAll)
                .andRoute(GET("/api/cie10/all"), cie10Handler::findAll)
                .andRoute(GET("/api/cie10/{id}"), cie10Handler::findById)

                .andRoute(GET("/api/capacitaciones"), capacitacionHandler::findAll)
                .andRoute(GET("/api/capacitaciones/all"), capacitacionHandler::findAll)
                .andRoute(GET("/api/capacitaciones/{id}"), capacitacionHandler::findById)
                .andRoute(POST("/api/capacitaciones"), capacitacionHandler::save)
                .andRoute(PUT("/api/capacitaciones"), capacitacionHandler::save)
                .andRoute(DELETE("/api/capacitaciones/{id}"), capacitacionHandler::delete)

                .andRoute(GET("/api/contratos"), contratoHandler::findAll)
                .andRoute(GET("/api/contratos/all"), contratoHandler::findAll)
                .andRoute(GET("/api/contratos/{id}"), contratoHandler::findById)
                .andRoute(POST("/api/contratos"), contratoHandler::save)
                .andRoute(PUT("/api/contratos"), contratoHandler::save)
                .andRoute(DELETE("/api/contratos/{id}"), contratoHandler::delete)

                .andRoute(GET("/api/documentos"), documentoHandler::findAll)
                .andRoute(GET("/api/documentos/all"), documentoHandler::findAll)
                .andRoute(GET("/api/documentos/{id}"), documentoHandler::findById)
                .andRoute(POST("/api/documentos"), documentoHandler::save)
                .andRoute(PUT("/api/documentos"), documentoHandler::save)
                .andRoute(DELETE("/api/documentos/{id}"), documentoHandler::delete)

                .andRoute(GET("/api/entrega-dye"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entrega-dye/all"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entrega-dye/{id}"), entregaDyEHandler::findById)
                .andRoute(POST("/api/entrega-dye"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entrega-dye"), entregaDyEHandler::save)
                .andRoute(DELETE("/api/entrega-dye/{id}"), entregaDyEHandler::delete)

                .andRoute(GET("/api/examenes"), examenHandler::findAll)
                .andRoute(GET("/api/examenes/all"), examenHandler::findAll)
                .andRoute(GET("/api/examenes/{id}"), examenHandler::findById)
                .andRoute(POST("/api/examenes"), examenHandler::save)
                .andRoute(PUT("/api/examenes"), examenHandler::save)
                .andRoute(DELETE("/api/examenes/{id}"), examenHandler::delete)

                .andRoute(GET("/api/incapacidades"), incapacidadHandler::findAll)
                .andRoute(GET("/api/incapacidades/all"), incapacidadHandler::findAll)
                .andRoute(GET("/api/incapacidades/{id}"), incapacidadHandler::findById)
                .andRoute(POST("/api/incapacidades"), incapacidadHandler::save)
                .andRoute(PUT("/api/incapacidades"), incapacidadHandler::save)
                .andRoute(DELETE("/api/incapacidades/{id}"), incapacidadHandler::delete)

                .andRoute(GET("/api/recomendaciones"), recommendationHandler::findAll)
                .andRoute(GET("/api/recomendaciones/all"), recommendationHandler::findAll)
                .andRoute(GET("/api/recomendaciones/{id}"), recommendationHandler::findById)
                .andRoute(POST("/api/recomendaciones"), recommendationHandler::save)
                .andRoute(PUT("/api/recomendaciones"), recommendationHandler::save)
                .andRoute(DELETE("/api/recomendaciones/{id}"), recommendationHandler::delete)

                .andRoute(GET("/api/vacaciones"), vacacionesHandler::findAll)
                .andRoute(GET("/api/vacaciones/all"), vacacionesHandler::findAll)
                .andRoute(GET("/api/vacaciones/{id}"), vacacionesHandler::findById)
                .andRoute(POST("/api/vacaciones"), vacacionesHandler::save)
                .andRoute(PUT("/api/vacaciones"), vacacionesHandler::save)
                .andRoute(DELETE("/api/vacaciones/{id}"), vacacionesHandler::delete);
    }
}
