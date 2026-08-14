-- PostgreSQL DDL for Sig-Backend (R2DBC)

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(140) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id VARCHAR(36) NOT NULL,
    role_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS eps (
    nit VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(150),
    telefono VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS arl (
    nit VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(150),
    telefono VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS afp (
    nit VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(150),
    telefono VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS caja_com_familiar (
    nit VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(150),
    telefono VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS cie10 (
    codigo VARCHAR(20) PRIMARY KEY,
    descripcion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS empleados (
    cedula VARCHAR(50) PRIMARY KEY,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    genero VARCHAR(20),
    fecha_nacimiento VARCHAR(50),
    tipo_sangre VARCHAR(10),
    direccion VARCHAR(150),
    municipio VARCHAR(100),
    telefono VARCHAR(50),
    eps_nit VARCHAR(50),
    afp_nit VARCHAR(50),
    arl_nit VARCHAR(50),
    caja_com_familiar_nit VARCHAR(50),
    alergia VARCHAR(255),
    medicamentos VARCHAR(255),
    en_caso_emergencia VARCHAR(100),
    parentesco VARCHAR(50),
    tel_emergencia VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS capacitaciones (
    id_capacitacion VARCHAR(36) PRIMARY KEY,
    tema VARCHAR(255),
    fecha DATE
);

CREATE TABLE IF NOT EXISTS contratos (
    id_contrato VARCHAR(36) PRIMARY KEY,
    tipo_contrato VARCHAR(50),
    fecha_inicio DATE,
    fecha_fin DATE,
    salario DOUBLE PRECISION,
    empleado_cedula VARCHAR(50),
    liquidado BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS documentos (
    id_documento VARCHAR(36) PRIMARY KEY,
    empleado_cedula VARCHAR(50),
    tipo VARCHAR(50),
    nombre VARCHAR(255),
    create_at TIMESTAMP,
    archivo BYTEA
);

CREATE TABLE IF NOT EXISTS entrega_dye (
    id_entrega_dye VARCHAR(36) PRIMARY KEY,
    fecha_entrega_dye DATE,
    descripcion VARCHAR(255),
    tipo VARCHAR(50),
    empleado_cedula VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS examenes (
    id_examen VARCHAR(36) PRIMARY KEY,
    fecha DATE,
    concepto BOOLEAN DEFAULT TRUE,
    restriccion VARCHAR(255) DEFAULT 'Sin Restricción',
    tipo_examen VARCHAR(50),
    contrato_id VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS incapacidades (
    id_incapacidad VARCHAR(36) PRIMARY KEY,
    fecha_inicio DATE,
    fecha_fin DATE,
    entidad VARCHAR(100),
    enfermedad VARCHAR(255),
    cie10_codigo VARCHAR(20),
    empleado_cedula VARCHAR(50),
    estado VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS recomendaciones (
    id_recomendaciones VARCHAR(36) PRIMARY KEY,
    examen_id VARCHAR(36),
    recommendation VARCHAR(500),
    tipo_seguimiento VARCHAR(50),
    primera_seguimiento VARCHAR(255),
    segunda_seguimiento VARCHAR(255),
    tercera_seguimiento VARCHAR(255),
    create_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vacaciones (
    id_vacaciones VARCHAR(36) PRIMARY KEY,
    fecha_inicio DATE,
    fecha_fin DATE,
    contrato_id VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS fotos (
    id_foto VARCHAR(36) PRIMARY KEY,
    foto BYTEA
);

CREATE TABLE IF NOT EXISTS items (
    id_items VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(100),
    marca VARCHAR(100),
    talla VARCHAR(20),
    categoria VARCHAR(50),
    serial VARCHAR(100),
    color VARCHAR(50)
);

-- Seed Initial Roles if not exists
INSERT INTO roles (id, name) VALUES ('1', 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO roles (id, name) VALUES ('2', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (id, name) VALUES ('3', 'ROLE_MODERATOR') ON CONFLICT DO NOTHING;
INSERT INTO roles (id, name) VALUES ('4', 'ROLE_SUPERVISOR') ON CONFLICT DO NOTHING;
