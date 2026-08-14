package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.JwtResponse;
import co.com.bancolombia.api.dto.LoginRequest;
import co.com.bancolombia.api.dto.MessageResponse;
import co.com.bancolombia.api.dto.SignupRequest;
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
import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.Examen;
import co.com.bancolombia.model.Incapacidad;
import co.com.bancolombia.model.Recommendation;
import co.com.bancolombia.model.Vacaciones;
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
import org.springframework.http.MediaType;
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
            // Auth
            @RouterOperation(path = "/api/auth/signin", method = RequestMethod.POST, beanClass = AuthHandler.class, beanMethod = "signin",
                    operation = @Operation(operationId = "signin", summary = "Iniciar sesión", tags = {"Autenticación"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LoginRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
                                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
                            })),
            @RouterOperation(path = "/api/auth/signup", method = RequestMethod.POST, beanClass = AuthHandler.class, beanMethod = "signup",
                    operation = @Operation(operationId = "signup", summary = "Registrar nuevo usuario", tags = {"Autenticación"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = SignupRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                                    @ApiResponse(responseCode = "400", description = "Usuario o email ya existe", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
                            })),

            // Empleados
            @RouterOperation(path = "/api/empleados", method = RequestMethod.GET, beanClass = EmpleadoHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllEmpleados", summary = "Obtener todos los empleados", tags = {"Empleados"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de empleados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class))))})),
            @RouterOperation(path = "/api/empleados/{id}", method = RequestMethod.GET, beanClass = EmpleadoHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findEmpleadoById", summary = "Buscar empleado por ID", tags = {"Empleados"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "ID del empleado")},
                            responses = {@ApiResponse(responseCode = "200", description = "Empleado encontrado", content = @Content(schema = @Schema(implementation = Empleado.class)))})),
            @RouterOperation(path = "/api/empleados", method = RequestMethod.POST, beanClass = EmpleadoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createEmpleado", summary = "Crear nuevo empleado", tags = {"Empleados"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Empleado.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Empleado creado", content = @Content(schema = @Schema(implementation = Empleado.class)))})),
            @RouterOperation(path = "/api/empleados", method = RequestMethod.PUT, beanClass = EmpleadoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateEmpleado", summary = "Actualizar empleado", tags = {"Empleados"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Empleado.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Empleado actualizado", content = @Content(schema = @Schema(implementation = Empleado.class)))})),
            @RouterOperation(path = "/api/empleados/{id}", method = RequestMethod.DELETE, beanClass = EmpleadoHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteEmpleado", summary = "Eliminar empleado por ID", tags = {"Empleados"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "ID del empleado")},
                            responses = {@ApiResponse(responseCode = "200", description = "Empleado eliminado")})),

            // Capacitaciones
            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.GET, beanClass = CapacitacionHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllCapacitaciones", summary = "Obtener capacitaciones", tags = {"Capacitaciones"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de capacitaciones", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Capacitacion.class))))})),
            @RouterOperation(path = "/api/capacitaciones/{id}", method = RequestMethod.GET, beanClass = CapacitacionHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findCapacitacionById", summary = "Buscar capacitación por ID", tags = {"Capacitaciones"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Capacitacion.class)))})),
            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.POST, beanClass = CapacitacionHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createCapacitacion", summary = "Crear capacitación", tags = {"Capacitaciones"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Capacitacion.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Capacitacion.class)))})),
            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.PUT, beanClass = CapacitacionHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateCapacitacion", summary = "Actualizar capacitación", tags = {"Capacitaciones"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Capacitacion.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Capacitacion.class)))})),
            @RouterOperation(path = "/api/capacitaciones/{id}", method = RequestMethod.DELETE, beanClass = CapacitacionHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteCapacitacion", summary = "Eliminar capacitación", tags = {"Capacitaciones"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Contratos
            @RouterOperation(path = "/api/contratos", method = RequestMethod.GET, beanClass = ContratoHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllContratos", summary = "Obtener contratos", tags = {"Contratos"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contrato.class))))})),
            @RouterOperation(path = "/api/contratos/{id}", method = RequestMethod.GET, beanClass = ContratoHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findContratoById", summary = "Buscar contrato por ID", tags = {"Contratos"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contrato.class)))})),
            @RouterOperation(path = "/api/contratos", method = RequestMethod.POST, beanClass = ContratoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createContrato", summary = "Crear contrato", tags = {"Contratos"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Contrato.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contrato.class)))})),
            @RouterOperation(path = "/api/contratos", method = RequestMethod.PUT, beanClass = ContratoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateContrato", summary = "Actualizar contrato", tags = {"Contratos"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Contrato.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contrato.class)))})),
            @RouterOperation(path = "/api/contratos/{id}", method = RequestMethod.DELETE, beanClass = ContratoHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteContrato", summary = "Eliminar contrato", tags = {"Contratos"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Documentos
            @RouterOperation(path = "/api/documentos", method = RequestMethod.GET, beanClass = DocumentoHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllDocumentos", summary = "Obtener documentos", tags = {"Documentos"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Documento.class))))})),
            @RouterOperation(path = "/api/documentos/{id}", method = RequestMethod.GET, beanClass = DocumentoHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findDocumentoById", summary = "Buscar documento por ID", tags = {"Documentos"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Documento.class)))})),
            @RouterOperation(path = "/api/documentos", method = RequestMethod.POST, beanClass = DocumentoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createDocumento", summary = "Crear documento", tags = {"Documentos"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Documento.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Documento.class)))})),
            @RouterOperation(path = "/api/documentos", method = RequestMethod.PUT, beanClass = DocumentoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateDocumento", summary = "Actualizar documento", tags = {"Documentos"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Documento.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Documento.class)))})),
            @RouterOperation(path = "/api/documentos/{id}", method = RequestMethod.DELETE, beanClass = DocumentoHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteDocumento", summary = "Eliminar documento", tags = {"Documentos"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Entrega DyE
            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.GET, beanClass = EntregaDyEHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllEntregaDyE", summary = "Obtener entregas de dotación y elementos", tags = {"Entrega DyE"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EntregaDyE.class))))})),
            @RouterOperation(path = "/api/entrega-dye/{id}", method = RequestMethod.GET, beanClass = EntregaDyEHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findEntregaDyEById", summary = "Buscar entrega por ID", tags = {"Entrega DyE"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EntregaDyE.class)))})),
            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.POST, beanClass = EntregaDyEHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createEntregaDyE", summary = "Crear entrega", tags = {"Entrega DyE"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = EntregaDyE.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EntregaDyE.class)))})),
            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.PUT, beanClass = EntregaDyEHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateEntregaDyE", summary = "Actualizar entrega", tags = {"Entrega DyE"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = EntregaDyE.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EntregaDyE.class)))})),
            @RouterOperation(path = "/api/entrega-dye/{id}", method = RequestMethod.DELETE, beanClass = EntregaDyEHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteEntregaDyE", summary = "Eliminar entrega", tags = {"Entrega DyE"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Exámenes
            @RouterOperation(path = "/api/examenes", method = RequestMethod.GET, beanClass = ExamenHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllExamenes", summary = "Obtener exámenes", tags = {"Exámenes"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Examen.class))))})),
            @RouterOperation(path = "/api/examenes/{id}", method = RequestMethod.GET, beanClass = ExamenHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findExamenById", summary = "Buscar examen por ID", tags = {"Exámenes"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Examen.class)))})),
            @RouterOperation(path = "/api/examenes", method = RequestMethod.POST, beanClass = ExamenHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createExamen", summary = "Crear examen", tags = {"Exámenes"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Examen.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Examen.class)))})),
            @RouterOperation(path = "/api/examenes", method = RequestMethod.PUT, beanClass = ExamenHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateExamen", summary = "Actualizar examen", tags = {"Exámenes"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Examen.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Examen.class)))})),
            @RouterOperation(path = "/api/examenes/{id}", method = RequestMethod.DELETE, beanClass = ExamenHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteExamen", summary = "Eliminar examen", tags = {"Exámenes"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Incapacidades
            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.GET, beanClass = IncapacidadHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllIncapacidades", summary = "Obtener incapacidades", tags = {"Incapacidades"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incapacidad.class))))})),
            @RouterOperation(path = "/api/incapacidades/{id}", method = RequestMethod.GET, beanClass = IncapacidadHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findIncapacidadById", summary = "Buscar incapacidad por ID", tags = {"Incapacidades"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Incapacidad.class)))})),
            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.POST, beanClass = IncapacidadHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createIncapacidad", summary = "Crear incapacidad", tags = {"Incapacidades"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Incapacidad.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Incapacidad.class)))})),
            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.PUT, beanClass = IncapacidadHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateIncapacidad", summary = "Actualizar incapacidad", tags = {"Incapacidades"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Incapacidad.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Incapacidad.class)))})),
            @RouterOperation(path = "/api/incapacidades/{id}", method = RequestMethod.DELETE, beanClass = IncapacidadHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteIncapacidad", summary = "Eliminar incapacidad", tags = {"Incapacidades"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Recomendaciones
            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.GET, beanClass = RecommendationHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllRecomendaciones", summary = "Obtener recomendaciones", tags = {"Recomendaciones"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recommendation.class))))})),
            @RouterOperation(path = "/api/recomendaciones/{id}", method = RequestMethod.GET, beanClass = RecommendationHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findRecomendacionById", summary = "Buscar recomendación por ID", tags = {"Recomendaciones"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Recommendation.class)))})),
            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.POST, beanClass = RecommendationHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createRecomendacion", summary = "Crear recomendación", tags = {"Recomendaciones"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Recommendation.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Recommendation.class)))})),
            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.PUT, beanClass = RecommendationHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateRecomendacion", summary = "Actualizar recomendación", tags = {"Recomendaciones"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Recommendation.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Recommendation.class)))})),
            @RouterOperation(path = "/api/recomendaciones/{id}", method = RequestMethod.DELETE, beanClass = RecommendationHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteRecomendacion", summary = "Eliminar recomendación", tags = {"Recomendaciones"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")})),

            // Vacaciones
            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.GET, beanClass = VacacionesHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllVacaciones", summary = "Obtener vacaciones", tags = {"Vacaciones"},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vacaciones.class))))})),
            @RouterOperation(path = "/api/vacaciones/{id}", method = RequestMethod.GET, beanClass = VacacionesHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findVacacionesById", summary = "Buscar vacaciones por ID", tags = {"Vacaciones"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Vacaciones.class)))})),
            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.POST, beanClass = VacacionesHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "createVacaciones", summary = "Crear vacaciones", tags = {"Vacaciones"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Vacaciones.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Vacaciones.class)))})),
            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.PUT, beanClass = VacacionesHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "updateVacaciones", summary = "Actualizar vacaciones", tags = {"Vacaciones"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Vacaciones.class))),
                            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Vacaciones.class)))})),
            @RouterOperation(path = "/api/vacaciones/{id}", method = RequestMethod.DELETE, beanClass = VacacionesHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "deleteVacaciones", summary = "Eliminar vacaciones", tags = {"Vacaciones"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true)},
                            responses = {@ApiResponse(responseCode = "200")}))
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
