# Sig-Backend

Backend reactivo para la gestión integral de seguridad industrial, salud ocupacional y administración de personal, desarrollado en Java bajo el patrón de **Arquitectura Hexagonal (Clean Architecture)** utilizando el **Scaffold Clean Architecture de Bancolombia**.

---

## 🚀 Stack Tecnológico

- **Lenguaje:** Java 25
- **Framework Principal:** Spring Boot 4 Reactivo (Spring WebFlux)
- **Persistencia Reactiva:** Spring Data R2DBC + PostgreSQL
- **Seguridad:** Spring Security WebFlux + JWT (JJWT `0.13.0`)
- **Documentación de API:** Springdoc OpenAPI `2.8.5` + **Scalar API Reference** (`@scalar/api-reference`)
- **Programación Reactiva:** Project Reactor (`Mono`, `Flux`)
- **Herramientas:** Lombok, Gradle

---

## 🏛️ Arquitectura del Proyecto

El proyecto está estructurado en Gradle multimódulo bajo la Arquitectura Hexagonal de Bancolombia:

```
Sig-Backend/
├── domain/
│   ├── model/                     # Modelos y entidades de negocio puras (User, Empleado, etc.) y Puertos (Gateways)
│   └── usecase/                   # Casos de uso de negocio puros (UserUseCase, EmpleadoUseCase, etc.)
├── infrastructure/
│   ├── driven-adapters/
│   │   └── r2dbc-postgresql/      # Adaptador de persistencia reactiva R2DBC + PostgreSQL
│   └── entry-points/
│       └── reactive-web/          # Puntos de entrada HTTP con Router Functions, Handlers y Scalar API Reference
└── applications/
    └── app-service/               # Módulo ejecutable Spring Boot, SecurityConfig y schema.sql
```

---

## 📖 Documentación de APIs e Interfaz Interactiva

El proyecto integra **Scalar API Reference** para probar y explorar la API de forma visual e interactiva:

- **Interfaz de Scalar:** `http://localhost:8080/scalar` o `http://localhost:8080/docs`
- **Especificación OpenAPI (JSON):** `http://localhost:8080/v3/api-docs`

---

## 🔐 Autenticación JWT (`0.13.0`)

El módulo de seguridad incluye un flujo de autenticación reactivo basado en tokens JWT firmados mediante la librería JJWT versión `0.13.0`.

### Endpoints de Autenticación

#### 1. Registro de Usuario (`POST /api/auth/signup`)
```bash
curl http://localhost:8080/api/auth/signup \
  --request POST \
  --header 'Content-Type: application/json' \
  --data '{
    "username": "admin",
    "name": "ADMINISTRADOR",
    "email": "admin@test.com",
    "password": "admin",
    "role": ["user", "admin"]
  }'
```

#### 2. Inicio de Sesión (`POST /api/auth/signin`)
```bash
curl http://localhost:8080/api/auth/signin \
  --request POST \
  --header 'Content-Type: application/json' \
  --data '{
    "username": "admin",
    "password": "admin"
  }'
```

---

## 📦 Módulos de Dominio Expuestos

La aplicación expone Router Functions para los siguientes módulos de negocio:

- `/api/auth` (Autenticación y Registro)
- `/api/empleados` (Gestión de Empleados)
- `/api/capacitaciones` (Capacitaciones)
- `/api/contratos` (Contratos laborales)
- `/api/documentos` (Documentos adjuntos)
- `/api/entrega-dye` (Entrega de dotación y elementos)
- `/api/examenes` (Exámenes médicos)
- `/api/incapacidades` (Incapacidades laborales)
- `/api/recomendaciones` (Recomendaciones médicas y seguimiento)
- `/api/vacaciones` (Solicitud y control de vacaciones)

---

## 🛠️ Compilación y Ejecución

### Requisitos Previos
- Java 25 JDK
- PostgreSQL (Base de datos `sig_db`)

### Compilación y Pruebas
```bash
.\gradlew build
```

### Ejecutar Servidor Localmente
```bash
.\gradlew :app-service:bootRun
```
