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
import co.com.bancolombia.api.handler.FotoHandler;
import co.com.bancolombia.api.handler.IncapacidadHandler;
import co.com.bancolombia.api.handler.ItemHandler;
import co.com.bancolombia.api.handler.RecommendationHandler;
import co.com.bancolombia.api.handler.VacacionesHandler;
import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.Foto;
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
            @RouterOperation(path = "/api/empleados/all", method = RequestMethod.GET, beanClass = EmpleadoHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllEmpleadosAll", summary = "Obtener todos los empleados (/all)", tags = {"Empleados"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de empleados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class))))})),
            @RouterOperation(path = "/api/empleados/{id}", method = RequestMethod.GET, beanClass = EmpleadoHandler.class, beanMethod = "findById",
                    operation = @Operation(operationId = "findEmpleadoById", summary = "Buscar empleado por ID", tags = {"Empleados"},
                            parameters = {@Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "ID del empleado")},
                            responses = {@ApiResponse(responseCode = "200", description = "Empleado encontrado", content = @Content(schema = @Schema(implementation = Empleado.class)))})),
            @RouterOperation(path = "/api/empleados/save", method = RequestMethod.POST, beanClass = EmpleadoHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "saveEmpleado", summary = "Guardar/Crear empleado (/save)", tags = {"Empleados"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Empleado.class))),
                            responses = {@ApiResponse(responseCode = "200", description = "Empleado guardado", content = @Content(schema = @Schema(implementation = Empleado.class)))})),

            // EPS
            @RouterOperation(path = "/api/eps", method = RequestMethod.GET, beanClass = EpsHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllEps", summary = "Obtener todas las EPS", tags = {"EPS"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de EPS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Eps.class))))})),
            @RouterOperation(path = "/api/eps/all", method = RequestMethod.GET, beanClass = EpsHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllEpsAll", summary = "Obtener todas las EPS (/all)", tags = {"EPS"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de EPS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Eps.class))))})),

            // ARL
            @RouterOperation(path = "/api/arl", method = RequestMethod.GET, beanClass = ArlHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllArl", summary = "Obtener todas las ARL", tags = {"ARL"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de ARL", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Arl.class))))})),
            @RouterOperation(path = "/api/arl/all", method = RequestMethod.GET, beanClass = ArlHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllArlAll", summary = "Obtener todas las ARL (/all)", tags = {"ARL"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de ARL", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Arl.class))))})),

            // AFP
            @RouterOperation(path = "/api/afp", method = RequestMethod.GET, beanClass = AfpHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllAfp", summary = "Obtener todas las AFP", tags = {"AFP"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de AFP", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Afp.class))))})),
            @RouterOperation(path = "/api/afp/all", method = RequestMethod.GET, beanClass = AfpHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllAfpAll", summary = "Obtener todas las AFP (/all)", tags = {"AFP"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de AFP", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Afp.class))))})),

            // Caja
            @RouterOperation(path = "/api/caja", method = RequestMethod.GET, beanClass = CajaComFamiliarHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllCaja", summary = "Obtener Cajas de Compensación", tags = {"Caja Compensación"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de Cajas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CajaComFamiliar.class))))})),
            @RouterOperation(path = "/api/caja/all", method = RequestMethod.GET, beanClass = CajaComFamiliarHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllCajaAll", summary = "Obtener Cajas de Compensación (/all)", tags = {"Caja Compensación"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de Cajas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CajaComFamiliar.class))))})),

            // Items
            @RouterOperation(path = "/api/items", method = RequestMethod.GET, beanClass = ItemHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllItems", summary = "Obtener Items/EPP", tags = {"Items / EPP"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de Items", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Items.class))))})),
            @RouterOperation(path = "/api/items/all", method = RequestMethod.GET, beanClass = ItemHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllItemsAll", summary = "Obtener Items/EPP (/all)", tags = {"Items / EPP"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de Items", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Items.class))))})),

            // CIE10
            @RouterOperation(path = "/api/cie10", method = RequestMethod.GET, beanClass = CIE10Handler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllCie10", summary = "Obtener Diagnósticos CIE10", tags = {"CIE10"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de CIE10", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CIE10.class))))})),
            @RouterOperation(path = "/api/cie10/all", method = RequestMethod.GET, beanClass = CIE10Handler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllCie10All", summary = "Obtener Diagnósticos CIE10 (/all)", tags = {"CIE10"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de CIE10", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CIE10.class))))})),

            // Capacitaciones
            @RouterOperation(path = "/api/capacitaciones", method = RequestMethod.GET, beanClass = CapacitacionHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllCapacitaciones", summary = "Obtener capacitaciones", tags = {"Capacitaciones"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de capacitaciones", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Capacitacion.class))))})),

            // Contratos
            @RouterOperation(path = "/api/contratos", method = RequestMethod.GET, beanClass = ContratoHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllContratos", summary = "Obtener contratos", tags = {"Contratos"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de contratos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contrato.class))))})),

            // Documentos
            @RouterOperation(path = "/api/documentos", method = RequestMethod.GET, beanClass = DocumentoHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllDocumentos", summary = "Obtener documentos", tags = {"Documentos"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de documentos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Documento.class))))})),

            // Entrega DyE
            @RouterOperation(path = "/api/entrega-dye", method = RequestMethod.GET, beanClass = EntregaDyEHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllEntregaDyE", summary = "Obtener entregas DyE", tags = {"Entrega DyE"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de entregas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EntregaDyE.class))))})),

            // Exámenes
            @RouterOperation(path = "/api/examenes", method = RequestMethod.GET, beanClass = ExamenHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllExamenes", summary = "Obtener exámenes ocupacionales", tags = {"Exámenes"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de exámenes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Examen.class))))})),

            // Incapacidades
            @RouterOperation(path = "/api/incapacidades", method = RequestMethod.GET, beanClass = IncapacidadHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllIncapacidades", summary = "Obtener incapacidades", tags = {"Incapacidades"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de incapacidades", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incapacidad.class))))})),

            // Recomendaciones
            @RouterOperation(path = "/api/recomendaciones", method = RequestMethod.GET, beanClass = RecommendationHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllRecomendaciones", summary = "Obtener recomendaciones", tags = {"Recomendaciones"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de recomendaciones", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recommendation.class))))})),

            // Vacaciones
            @RouterOperation(path = "/api/vacaciones", method = RequestMethod.GET, beanClass = VacacionesHandler.class, beanMethod = "findAll",
                    operation = @Operation(operationId = "findAllVacaciones", summary = "Obtener vacaciones", tags = {"Vacaciones"},
                            responses = {@ApiResponse(responseCode = "200", description = "Lista de vacaciones", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vacaciones.class))))}))
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
            VacacionesHandler vacacionesHandler,
            EpsHandler epsHandler,
            ArlHandler arlHandler,
            AfpHandler afpHandler,
            CajaComFamiliarHandler cajaComFamiliarHandler,
            ItemHandler itemHandler,
            CIE10Handler cie10Handler,
            FotoHandler fotoHandler
    ) {
        return route(POST("/api/auth/signin"), authHandler::signin)
                .andRoute(POST("/api/auth/signup"), authHandler::signup)

                .andRoute(POST("/api/fotos"), fotoHandler::save)
                .andRoute(POST("/api/fotos/save"), fotoHandler::save)
                .andRoute(GET("/api/fotos/{id}"), fotoHandler::findById)
                .andRoute(GET("/api/fotos/image/{id}"), fotoHandler::findById)

                .andRoute(GET("/api/empleados"), empleadoHandler::findAll)
                .andRoute(GET("/api/empleados/all"), empleadoHandler::findAll)
                .andRoute(GET("/api/empleados/{id}"), empleadoHandler::findById)
                .andRoute(POST("/api/empleados"), empleadoHandler::save)
                .andRoute(POST("/api/empleados/save"), empleadoHandler::save)
                .andRoute(PUT("/api/empleados"), empleadoHandler::save)
                .andRoute(PUT("/api/empleados/save"), empleadoHandler::save)
                .andRoute(DELETE("/api/empleados/{id}"), empleadoHandler::delete)
                .andRoute(DELETE("/api/empleados/delete/{id}"), empleadoHandler::delete)
                .andRoute(GET("/api/empleados/delete/{id}"), empleadoHandler::delete)

                .andRoute(GET("/api/eps"), epsHandler::findAll)
                .andRoute(GET("/api/eps/all"), epsHandler::findAll)
                .andRoute(GET("/api/eps/{id}"), epsHandler::findById)
                .andRoute(POST("/api/eps"), epsHandler::save)
                .andRoute(POST("/api/eps/save"), epsHandler::save)
                .andRoute(PUT("/api/eps"), epsHandler::save)
                .andRoute(PUT("/api/eps/save"), epsHandler::save)
                .andRoute(DELETE("/api/eps/{id}"), epsHandler::delete)
                .andRoute(DELETE("/api/eps/delete/{id}"), epsHandler::delete)
                .andRoute(GET("/api/eps/delete/{id}"), epsHandler::delete)

                .andRoute(GET("/api/arl"), arlHandler::findAll)
                .andRoute(GET("/api/arl/all"), arlHandler::findAll)
                .andRoute(GET("/api/arl/{id}"), arlHandler::findById)
                .andRoute(POST("/api/arl"), arlHandler::save)
                .andRoute(POST("/api/arl/save"), arlHandler::save)
                .andRoute(PUT("/api/arl"), arlHandler::save)
                .andRoute(PUT("/api/arl/save"), arlHandler::save)
                .andRoute(DELETE("/api/arl/{id}"), arlHandler::delete)
                .andRoute(DELETE("/api/arl/delete/{id}"), arlHandler::delete)
                .andRoute(GET("/api/arl/delete/{id}"), arlHandler::delete)

                .andRoute(GET("/api/afp"), afpHandler::findAll)
                .andRoute(GET("/api/afp/all"), afpHandler::findAll)
                .andRoute(GET("/api/afp/{id}"), afpHandler::findById)
                .andRoute(POST("/api/afp"), afpHandler::save)
                .andRoute(POST("/api/afp/save"), afpHandler::save)
                .andRoute(PUT("/api/afp"), afpHandler::save)
                .andRoute(PUT("/api/afp/save"), afpHandler::save)
                .andRoute(DELETE("/api/afp/{id}"), afpHandler::delete)
                .andRoute(DELETE("/api/afp/delete/{id}"), afpHandler::delete)
                .andRoute(GET("/api/afp/delete/{id}"), afpHandler::delete)

                .andRoute(GET("/api/caja"), cajaComFamiliarHandler::findAll)
                .andRoute(GET("/api/caja/all"), cajaComFamiliarHandler::findAll)
                .andRoute(GET("/api/caja/{id}"), cajaComFamiliarHandler::findById)
                .andRoute(POST("/api/caja"), cajaComFamiliarHandler::save)
                .andRoute(POST("/api/caja/save"), cajaComFamiliarHandler::save)
                .andRoute(PUT("/api/caja"), cajaComFamiliarHandler::save)
                .andRoute(PUT("/api/caja/save"), cajaComFamiliarHandler::save)
                .andRoute(DELETE("/api/caja/{id}"), cajaComFamiliarHandler::delete)
                .andRoute(DELETE("/api/caja/delete/{id}"), cajaComFamiliarHandler::delete)
                .andRoute(GET("/api/caja/delete/{id}"), cajaComFamiliarHandler::delete)

                .andRoute(GET("/api/items"), itemHandler::findAll)
                .andRoute(GET("/api/items/all"), itemHandler::findAll)
                .andRoute(GET("/api/items/{id}"), itemHandler::findById)
                .andRoute(POST("/api/items"), itemHandler::save)
                .andRoute(POST("/api/items/save"), itemHandler::save)
                .andRoute(PUT("/api/items"), itemHandler::save)
                .andRoute(PUT("/api/items/save"), itemHandler::save)
                .andRoute(DELETE("/api/items/{id}"), itemHandler::delete)
                .andRoute(DELETE("/api/items/delete/{id}"), itemHandler::delete)
                .andRoute(GET("/api/items/delete/{id}"), itemHandler::delete)

                .andRoute(GET("/api/cie10"), cie10Handler::findAll)
                .andRoute(GET("/api/cie10/all"), cie10Handler::findAll)
                .andRoute(GET("/api/cie10/{id}"), cie10Handler::findById)
                .andRoute(POST("/api/cie10"), cie10Handler::save)
                .andRoute(POST("/api/cie10/save"), cie10Handler::save)
                .andRoute(PUT("/api/cie10"), cie10Handler::save)
                .andRoute(PUT("/api/cie10/save"), cie10Handler::save)
                .andRoute(DELETE("/api/cie10/{id}"), cie10Handler::delete)
                .andRoute(DELETE("/api/cie10/delete/{id}"), cie10Handler::delete)

                .andRoute(GET("/api/capacitaciones"), capacitacionHandler::findAll)
                .andRoute(GET("/api/capacitaciones/all"), capacitacionHandler::findAll)
                .andRoute(GET("/api/capacitaciones/empleado/{cedula}"), capacitacionHandler::findByEmpleado)
                .andRoute(GET("/api/capacitaciones/{id}"), capacitacionHandler::findById)
                .andRoute(POST("/api/capacitaciones"), capacitacionHandler::save)
                .andRoute(POST("/api/capacitaciones/save"), capacitacionHandler::save)
                .andRoute(PUT("/api/capacitaciones"), capacitacionHandler::save)
                .andRoute(PUT("/api/capacitaciones/save"), capacitacionHandler::save)
                .andRoute(PUT("/api/capacitaciones/{id}/asignar-empleados"), capacitacionHandler::asignarEmpleados)
                .andRoute(PUT("/api/capacitaciones/{id}/eliminar-empleados"), capacitacionHandler::eliminarEmpleado)
                .andRoute(DELETE("/api/capacitaciones/{id}"), capacitacionHandler::delete)
                .andRoute(DELETE("/api/capacitaciones/delete/{id}"), capacitacionHandler::delete)
                .andRoute(GET("/api/capacitaciones/delete/{id}"), capacitacionHandler::delete)

                .andRoute(GET("/api/contratos"), contratoHandler::findAll)
                .andRoute(GET("/api/contratos/all"), contratoHandler::findAll)
                .andRoute(GET("/api/contratos/{id}"), contratoHandler::findById)
                .andRoute(POST("/api/contratos"), contratoHandler::save)
                .andRoute(POST("/api/contratos/save"), contratoHandler::save)
                .andRoute(PUT("/api/contratos"), contratoHandler::save)
                .andRoute(PUT("/api/contratos/save"), contratoHandler::save)
                .andRoute(DELETE("/api/contratos/{id}"), contratoHandler::delete)
                .andRoute(DELETE("/api/contratos/delete/{id}"), contratoHandler::delete)
                .andRoute(GET("/api/contratos/delete/{id}"), contratoHandler::delete)

                .andRoute(GET("/api/documentos"), documentoHandler::findAll)
                .andRoute(GET("/api/documentos/all"), documentoHandler::findAll)
                .andRoute(GET("/api/documentos/{id}"), documentoHandler::findById)
                .andRoute(POST("/api/documentos"), documentoHandler::save)
                .andRoute(POST("/api/documentos/save"), documentoHandler::save)
                .andRoute(PUT("/api/documentos"), documentoHandler::save)
                .andRoute(PUT("/api/documentos/save"), documentoHandler::save)
                .andRoute(DELETE("/api/documentos/{id}"), documentoHandler::delete)
                .andRoute(DELETE("/api/documentos/delete/{id}"), documentoHandler::delete)
                .andRoute(GET("/api/documentos/delete/{id}"), documentoHandler::delete)

                .andRoute(GET("/api/entrega-dye"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entrega-dye/all"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entrega-dye/{id}"), entregaDyEHandler::findById)
                .andRoute(POST("/api/entrega-dye"), entregaDyEHandler::save)
                .andRoute(POST("/api/entrega-dye/save"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entrega-dye"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entrega-dye/save"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entrega-dye/{id}/items/cargar"), entregaDyEHandler::agregarItems)
                .andRoute(PUT("/api/entrega-dye/{id}/items/eliminar"), entregaDyEHandler::eliminarItem)
                .andRoute(DELETE("/api/entrega-dye/{id}"), entregaDyEHandler::delete)
                .andRoute(DELETE("/api/entrega-dye/delete/{id}"), entregaDyEHandler::delete)
                .andRoute(GET("/api/entrega-dye/delete/{id}"), entregaDyEHandler::delete)

                .andRoute(GET("/api/entregas"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entregas/all"), entregaDyEHandler::findAll)
                .andRoute(GET("/api/entregas/{id}"), entregaDyEHandler::findById)
                .andRoute(POST("/api/entregas"), entregaDyEHandler::save)
                .andRoute(POST("/api/entregas/save"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entregas"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entregas/save"), entregaDyEHandler::save)
                .andRoute(PUT("/api/entregas/{id}/items/cargar"), entregaDyEHandler::agregarItems)
                .andRoute(PUT("/api/entregas/{id}/items/eliminar"), entregaDyEHandler::eliminarItem)
                .andRoute(DELETE("/api/entregas/{id}"), entregaDyEHandler::delete)
                .andRoute(DELETE("/api/entregas/delete/{id}"), entregaDyEHandler::delete)
                .andRoute(GET("/api/entregas/delete/{id}"), entregaDyEHandler::delete)

                .andRoute(GET("/api/examenes"), examenHandler::findAll)
                .andRoute(GET("/api/examenes/all"), examenHandler::findAll)
                .andRoute(GET("/api/examenes/{id}"), examenHandler::findById)
                .andRoute(POST("/api/examenes"), examenHandler::save)
                .andRoute(POST("/api/examenes/save"), examenHandler::save)
                .andRoute(PUT("/api/examenes"), examenHandler::save)
                .andRoute(PUT("/api/examenes/save"), examenHandler::save)
                .andRoute(DELETE("/api/examenes/{id}"), examenHandler::delete)
                .andRoute(DELETE("/api/examenes/delete/{id}"), examenHandler::delete)
                .andRoute(GET("/api/examenes/delete/{id}"), examenHandler::delete)

                .andRoute(GET("/api/incapacidades"), incapacidadHandler::findAll)
                .andRoute(GET("/api/incapacidades/all"), incapacidadHandler::findAll)
                .andRoute(GET("/api/incapacidades/{id}"), incapacidadHandler::findById)
                .andRoute(POST("/api/incapacidades"), incapacidadHandler::save)
                .andRoute(POST("/api/incapacidades/save"), incapacidadHandler::save)
                .andRoute(PUT("/api/incapacidades"), incapacidadHandler::save)
                .andRoute(PUT("/api/incapacidades/save"), incapacidadHandler::save)
                .andRoute(DELETE("/api/incapacidades/{id}"), incapacidadHandler::delete)
                .andRoute(DELETE("/api/incapacidades/delete/{id}"), incapacidadHandler::delete)
                .andRoute(GET("/api/incapacidades/delete/{id}"), incapacidadHandler::delete)

                .andRoute(GET("/api/recomendaciones"), recommendationHandler::findAll)
                .andRoute(GET("/api/recomendaciones/all"), recommendationHandler::findAll)
                .andRoute(GET("/api/recomendaciones/{id}"), recommendationHandler::findById)
                .andRoute(POST("/api/recomendaciones"), recommendationHandler::save)
                .andRoute(POST("/api/recomendaciones/save"), recommendationHandler::save)
                .andRoute(PUT("/api/recomendaciones"), recommendationHandler::save)
                .andRoute(PUT("/api/recomendaciones/save"), recommendationHandler::save)
                .andRoute(DELETE("/api/recomendaciones/{id}"), recommendationHandler::delete)
                .andRoute(DELETE("/api/recomendaciones/delete/{id}"), recommendationHandler::delete)
                .andRoute(GET("/api/recomendaciones/delete/{id}"), recommendationHandler::delete)

                .andRoute(GET("/api/vacaciones"), vacacionesHandler::findAll)
                .andRoute(GET("/api/vacaciones/all"), vacacionesHandler::findAll)
                .andRoute(GET("/api/vacaciones/{id}"), vacacionesHandler::findById)
                .andRoute(POST("/api/vacaciones"), vacacionesHandler::save)
                .andRoute(POST("/api/vacaciones/save"), vacacionesHandler::save)
                .andRoute(PUT("/api/vacaciones"), vacacionesHandler::save)
                .andRoute(PUT("/api/vacaciones/save"), vacacionesHandler::save)
                .andRoute(DELETE("/api/vacaciones/{id}"), vacacionesHandler::delete)
                .andRoute(DELETE("/api/vacaciones/delete/{id}"), vacacionesHandler::delete)
                .andRoute(GET("/api/vacaciones/delete/{id}"), vacacionesHandler::delete);
    }
}
