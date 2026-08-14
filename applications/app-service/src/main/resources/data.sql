-- ==============================================================================
-- SIG Platform - Dummy Seed Data for Testing Modules (PostgreSQL / R2DBC)
-- Excludes: users, roles, user_roles, fotos, y documentos.
-- ==============================================================================

-- 1. EPS
INSERT INTO eps (nit, nombre, direccion, telefono) VALUES 
('800088702-2', 'EPS SURA', 'Calle 49 # 50-21, Medellín', '(604) 4441234'),
('800130907-4', 'EPS SANITAS', 'Carrera 7 # 99-53, Bogotá', '(601) 3759000'),
('860066942-7', 'COMPENSAR EPS', 'Calle 26 # 66-a-48, Bogotá', '(601) 4441234'),
('800251440-6', 'SALUD TOTAL EPS', 'Autopista Norte # 108-27, Bogotá', '(601) 4854555'),
('900156264-2', 'NUEVA EPS', 'Carrera 85K # 46A-66, Bogotá', '(601) 3077022')
ON CONFLICT DO NOTHING;

-- 2. ARL
INSERT INTO arl (nit, nombre, direccion, telefono) VALUES 
('890903407-9', 'ARL SURA', 'Carrera 43A # 1-85, Medellín', '(604) 4444547'),
('860011153-6', 'POSITIVA COMPAÑIA DE SEGUROS', 'Carrera 10 # 64-28, Bogotá', '(601) 3307000'),
('860002184-6', 'COLMENA SEGUROS ARL', 'Calle 72 # 10-07, Bogotá', '(601) 4010440'),
('860002503-4', 'AXA COLPATRIA ARL', 'Carrera 7 # 24-89, Bogotá', '(601) 4235700')
ON CONFLICT DO NOTHING;

-- 3. AFP (Fondos de Pensiones y Cesantías)
INSERT INTO afp (nit, nombre, direccion, telefono) VALUES 
('800229739-0', 'PROTECCION S.A.', 'Calle 49 # 50-21, Medellín', '(604) 4447555'),
('800149496-2', 'PORVENIR S.A.', 'Carrera 13 # 26A-65, Bogotá', '(601) 3077006'),
('800170494-1', 'COLFONDOS S.A.', 'Calle 67 # 7-35, Bogotá', '(601) 7484888'),
('860002162-8', 'SKANDIA PENSIONES', 'Avenida 19 # 109A-30, Bogotá', '(601) 6584000')
ON CONFLICT DO NOTHING;

-- 4. Caja de Compensación Familiar
INSERT INTO caja_com_familiar (nit, nombre, direccion, telefono) VALUES 
('890200106-1', 'COMFENALCO SANTANDER', 'Calle 54 # 31-100, Bucaramanga', '(607) 6577000'),
('860007336-1', 'COMPENSAR CAJA', 'Avenida 68 # 49-47, Bogotá', '(601) 3077001'),
('860007302-9', 'COLSUBSIDIO CAJA', 'Calle 26 # 25-50, Bogotá', '(601) 7450999'),
('860013570-3', 'CAFAM', 'Carrera 68 # 90-88, Bogotá', '(601) 3077011')
ON CONFLICT DO NOTHING;

-- 5. CIE-10 (Diagnósticos Médicos)
INSERT INTO cie10 (codigo, descripcion) VALUES 
('M54.5', 'Lumbago no especificado y dolor en columna lumbar'),
('J00', 'Rinofaringitis aguda (Resfriado común)'),
('S93.4', 'Esguince y torcedura de tobillo y pie'),
('G44.2', 'Cefalea de tipo tensional'),
('Z01.0', 'Examen de ojos y de la visión ocupacional')
ON CONFLICT DO NOTHING;

-- 6. Empleados
INSERT INTO empleados (cedula, nombres, apellidos, genero, fecha_nacimiento, tipo_sangre, direccion, municipio, telefono, eps_nit, afp_nit, arl_nit, caja_com_familiar_nit, alergia, medicamentos, en_caso_emergencia, parentesco, tel_emergencia) VALUES 
('1098765432', 'Carlos Alberto', 'Gómez Mendoza', 'Masculino', '1988-05-14', 'O+', 'Calle 34 # 23-45', 'Bucaramanga', '3001234567', '800088702-2', '800229739-0', '890903407-9', '890200106-1', 'Penicilina', 'Ninguno', 'Maria Gómez', 'Madre', '3159876543'),
('1095432189', 'Ana María', 'Rodríguez Silva', 'Femenino', '1992-11-20', 'A+', 'Carrera 27 # 48-12', 'Bucaramanga', '3124567890', '800130907-4', '800149496-2', '860011153-6', '890200106-1', 'Ninguna', 'Ninguno', 'Jorge Rodríguez', 'Esposo', '3016549870'),
('1098112233', 'Juan David', 'Martínez Suárez', 'Masculino', '1995-03-08', 'O-', 'Calle 56 # 14-89', 'Floridablanca', '3187654321', '860066942-7', '800170494-1', '860002184-6', '890200106-1', 'Polvo / Ácaros', 'Loratadina', 'Laura Martínez', 'Hermana', '3201239876'),
('1097654321', 'Diana Marcela', 'Hernández Castro', 'Femenino', '1990-08-30', 'B+', 'Carrera 19 # 35-67', 'Girón', '3019876543', '800251440-6', '800229739-0', '890903407-9', '890200106-1', 'Ninguna', 'Ninguno', 'Pedro Hernández', 'Padre', '3114561234'),
('1096543210', 'Luis Fernando', 'Pérez Restrepo', 'Masculino', '1985-12-05', 'AB+', 'Calle 105 # 22-10', 'Bucaramanga', '3153456789', '900156264-2', '800149496-2', '860002503-4', '890200106-1', 'Mariscos', 'Ninguno', 'Claudia Restrepo', 'Esposa', '3109871234')
ON CONFLICT DO NOTHING;

-- 7. Contratos
INSERT INTO contratos (id_contrato, tipo_contrato, fecha_inicio, fecha_fin, salario, empleado_cedula, liquidado) VALUES 
('CTR-001', 'Término Indefinido', '2022-01-15', '2028-12-31', 2500000.0, '1098765432', FALSE),
('CTR-002', 'Término Fijo', '2023-03-01', '2026-12-31', 1800000.0, '1095432189', FALSE),
('CTR-003', 'Obra o Labor', '2024-02-10', '2026-08-31', 2100000.0, '1098112233', FALSE),
('CTR-004', 'Término Indefinido', '2021-06-01', '2029-12-31', 3200000.0, '1097654321', FALSE),
('CTR-005', 'Término Fijo', '2024-01-02', '2025-01-02', 1950000.0, '1096543210', TRUE)
ON CONFLICT DO NOTHING;

-- 8. Capacitaciones SST
INSERT INTO capacitaciones (id_capacitacion, tema, fecha) VALUES 
('CAP-001', 'Manejo Seguro de Cargas y Salud Ergonomica en Transformadores', '2026-02-10'),
('CAP-002', 'Prevención de Riesgo Eléctrico y Trabajo en Altura HSEQ', '2026-03-15'),
('CAP-003', 'Uso Correcto de Elementos de Protección Personal (EPP)', '2026-04-20')
ON CONFLICT DO NOTHING;

-- 9. Exámenes Ocupacionales
INSERT INTO examenes (id_examen, fecha, concepto, restriccion, tipo_examen, contrato_id) VALUES 
('EXM-001', '2024-02-01', TRUE, 'Sin Restricción', 'Ingreso', 'CTR-001'),
('EXM-002', '2025-02-05', FALSE, 'No levantar cargas superiores a 15kg por molestia lumbar', 'Periódico', 'CTR-001'),
('EXM-003', '2024-03-01', TRUE, 'Sin Restricción', 'Ingreso', 'CTR-002'),
('EXM-004', '2024-02-10', TRUE, 'Sin Restricción', 'Ingreso', 'CTR-003'),
('EXM-005', '2025-01-02', TRUE, 'Sin Restricción', 'Egreso', 'CTR-005')
ON CONFLICT DO NOTHING;

-- 10. Recomendaciones Médicas
INSERT INTO recomendaciones (id_recomendaciones, examen_id, recommendation, tipo_seguimiento, primera_seguimiento, segunda_seguimiento, tercera_seguimiento, create_at) VALUES 
('REC-001', 'EXM-002', 'Pausas activas cada 2 horas y uso de faja lumbar en taller de transformadores', 'Trimestral', '2025-05-05', '2025-08-05', '2025-11-05', NOW())
ON CONFLICT DO NOTHING;

-- 11. Incapacidades
INSERT INTO incapacidades (id_incapacidad, fecha_inicio, fecha_fin, entidad, enfermedad, cie10_codigo, empleado_cedula, estado) VALUES 
('INC-001', '2026-01-10', '2026-01-15', 'EPS SURA', 'Enfermedad General', 'M54.5', '1098765432', 'Cobrada'),
('INC-002', '2026-02-20', '2026-02-23', 'EPS SANITAS', 'Enfermedad General', 'J00', '1095432189', 'En Tramite'),
('INC-003', '2026-03-05', '2026-03-12', 'ARL SURA', 'Accidente de Trabajo', 'S93.4', '1098112233', 'Pendiente')
ON CONFLICT DO NOTHING;

-- 12. Vacaciones
INSERT INTO vacaciones (id_vacaciones, fecha_inicio, fecha_fin, contrato_id) VALUES 
('VAC-001', '2025-12-15', '2025-12-30', 'CTR-001'),
('VAC-002', '2026-01-05', '2026-01-20', 'CTR-004')
ON CONFLICT DO NOTHING;

-- 13. Entregas EPP / Dotación
INSERT INTO entrega_dye (id_entrega_dye, fecha_entrega_dye, descripcion, tipo, empleado_cedula) VALUES 
('ENT-001', '2026-01-10', 'Entrega de botas dieléctricas t39 y casco dieléctrico azul', 'Dotación Operativa', '1098765432'),
('ENT-002', '2026-02-01', 'Entrega de kit de protección auditiva y guantes de carnaza', 'EPP Individual', '1095432189')
ON CONFLICT DO NOTHING;

-- 14. Catálogo de Items / EPP
INSERT INTO items (id_items, nombre, marca, talla, categoria, serial, color) VALUES 
('ITM-001', 'Botas Dieléctricas de Seguridad', 'Westland', '40', 'Calzado SST', 'SN-998877', 'Negro'),
('ITM-002', 'Casco Dieléctrico Tipo II', '3M', 'Única', 'Protección Cabeza', 'SN-112233', 'Azul'),
('ITM-003', 'Guantes de Nitrilo Alta Resistencia', 'Ansell', 'L', 'Protección Manos', 'SN-445566', 'Verde'),
('ITM-004', 'Chaleco Reflectivo de Seguridad', 'Vallen', 'M', 'Vestuario SST', 'SN-778899', 'Naranja Reflectivo'),
('ITM-005', 'Arnés Multiproposito 4 Argollas', 'SOSEGA', 'Ajustable', 'Trabajo en Altura', 'SN-334455', 'Amarillo/Negro')
ON CONFLICT DO NOTHING;
