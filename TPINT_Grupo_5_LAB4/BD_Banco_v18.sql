drop database IF EXISTS bdbanco;
CREATE DATABASE IF NOT EXISTS bdbanco;
USE bdbanco;

-- Table usuarios
DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
  Usuario varchar(50) NOT NULL,
  Contraseña varchar(45) NOT NULL,
  EsAdmin tinyint(1) NOT NULL,
  PRIMARY KEY (Usuario),
  UNIQUE KEY Usuario_UNIQUE (Usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table paises
DROP TABLE IF EXISTS paises;
CREATE TABLE paises (
  ID_Nacionalidad INT(5) NOT NULL AUTO_INCREMENT,
  Pais varchar(45) NOT NULL,
  PRIMARY KEY (ID_Nacionalidad),
  UNIQUE KEY Pais_UNIQUE (Pais)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table provincias
DROP TABLE IF EXISTS provincias;
CREATE TABLE provincias (
  ID_Provincia int(5) NOT NULL AUTO_INCREMENT,
  Provincia varchar(45) NOT NULL,
  PRIMARY KEY (ID_Provincia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table localidades
DROP TABLE IF EXISTS localidades;
CREATE TABLE localidades (
  ID_Localidad int(5) NOT NULL AUTO_INCREMENT,
  ID_Provincia int(5) NOT NULL,
  Localidad varchar(45) NOT NULL,
  PRIMARY KEY (ID_Localidad),
  UNIQUE KEY ID_Localidad_UNIQUE (ID_Localidad),
  CONSTRAINT fk_localidad_provincia FOREIGN KEY (ID_Provincia) REFERENCES provincias (ID_Provincia) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table clientes
DROP TABLE IF EXISTS InfoUsuarios;
CREATE TABLE InfoUsuarios (
  DNI int(10) NOT NULL,
  UsuarioCliente varchar(50) NOT NULL,
  CUIL varchar(50) NOT NULL,
  Nombre varchar(45) NOT NULL,
  Apellido varchar(45) NOT NULL,
  Sexo varchar(45) NOT NULL,
  ID_Nacionalidad int(5) NOT NULL,
  FechaNacimiento datetime NOT NULL,
  ID_Provincia int(5) NOT NULL,
  ID_Localidad int(5) NOT NULL,
  Direccion varchar(100) NOT NULL,
  Mail varchar(100) NOT NULL,
  Telefono1 varchar(10) NOT NULL,
  Telefono2 varchar(10) NULL,
  Estado tinyint(1) NOT NULL,
  PRIMARY KEY (DNI),
  UNIQUE KEY UsuarioCliente_UNIQUE (UsuarioCliente),
  UNIQUE KEY CUIL_UNIQUE (CUIL),
  CONSTRAINT fk_cliente_usuario FOREIGN KEY (UsuarioCliente) REFERENCES usuarios (Usuario) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_cliente_nacionalidad FOREIGN KEY (ID_Nacionalidad) REFERENCES paises (ID_Nacionalidad) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_cliente_provincia_localidad FOREIGN KEY (ID_Provincia, ID_Localidad) REFERENCES localidades (ID_Provincia, ID_Localidad) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tipodecuentas;
CREATE TABLE tipodecuentas (
	idTipoCuenta int(2) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Descripcion varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS cuentas;
CREATE TABLE cuentas (
  CBU varchar(22) NOT NULL PRIMARY KEY,
  NumeroCuenta bigint(14) AUTO_INCREMENT NOT NULL,
  DNI int(10) NOT NULL,
  idTipoCuenta int(2) NOT NULL,
  FechaCreacion datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  Saldo decimal(12, 2) DEFAULT 10000.00 NOT NULL, -- Valor predeterminado de 10,000
  Estado tinyint(1) NOT NULL DEFAULT 1,
  UNIQUE KEY NumeroCuenta_UNIQUE (NumeroCuenta),
  CONSTRAINT fk_cuenta_cliente FOREIGN KEY (DNI) REFERENCES InfoUsuarios (DNI) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_cuenta_tipodecuenta FOREIGN KEY (idTipoCuenta) REFERENCES tipodecuentas (idTipoCuenta) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table tipodemovimientos
DROP TABLE IF EXISTS tipodemovimientos;
CREATE TABLE tipodemovimientos (
  ID_TipoMovimiento int(11) NOT NULL AUTO_INCREMENT,
  NombreTipoMovimiento varchar(45) NOT NULL,
  PRIMARY KEY (ID_TipoMovimiento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table movimientos
DROP TABLE IF EXISTS movimientos;
CREATE TABLE movimientos (
  NroMovimiento int(11) NOT NULL AUTO_INCREMENT,
  CBU_Cliente varchar(22) NOT NULL,
  FechaMovimiento datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  Importe decimal(12, 2) NOT NULL, -- EL IMPORTE DEL MOVIMIENTO
  SaldoPosterior decimal(12, 2) NOT NULL, -- COMO QUEDA LA CUENTA DESPUES
  Detalle_Concepto varchar(150) NOT NULL,
  ID_TipoMovimiento int(11) NOT NULL,
  PRIMARY KEY (NroMovimiento),
  KEY fk_movimiento_cuenta (CBU_Cliente),
  KEY fk_movimiento_tipo (ID_TipoMovimiento),
  CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (CBU_Cliente) REFERENCES cuentas (CBU) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_movimiento_tipo FOREIGN KEY (ID_TipoMovimiento) REFERENCES tipodemovimientos (ID_TipoMovimiento) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tabla prestamos
DROP TABLE IF EXISTS prestamos;
CREATE TABLE prestamos (
  ID_Prestamo int(11) NOT NULL AUTO_INCREMENT,
  DNI_Cliente int(10) NOT NULL,
  FechaSolicitud datetime NOT NULL,
  ImportePedido decimal(12, 2) NOT NULL,
  PlazoMeses int(11) NOT NULL,
  CantidadCuotas int(11) NOT NULL,
  ImportePagar decimal(12, 2) NOT NULL,
  MontoCuota decimal(12, 2) NOT NULL,
  EstadoSolicitud tinyint(4) NOT NULL, -- Rechazado: 0, Aceptado: 1, En espera: 2
  EstadoPrestamo tinyint(4) NOT NULL, -- No pagado: 0, Pagado: 1
  CBU_Deposito varchar(22) NOT NULL,  -- CBU de depósito
  PRIMARY KEY (ID_Prestamo),
  -- UNIQUE KEY DNI_Cliente_UNIQUE (DNI_Cliente),
  CONSTRAINT fk_prestamo_info_usuario FOREIGN KEY (DNI_Cliente) REFERENCES InfoUsuarios (DNI) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabla pagoscuotas
DROP TABLE IF EXISTS pagoscuotas;
CREATE TABLE pagoscuotas (
  ID_Prestamo int(11) NOT NULL,
  NroCuota int(11) NOT NULL,
  FechaVencimiento datetime NOT NULL, -- Vencimiento de la cuota
  FechaPago datetime NULL,            -- Fecha en la que se pago Dicha cuota
  CBU_Origen varchar(22) NULL,
  ImportePago decimal(12, 2) NULL,
  EstadoCuota tinyint(4) NOT NULL default 0,
  PRIMARY KEY (ID_Prestamo, NroCuota),
  KEY fk_pago_cuota_cuenta (CBU_Origen),
  CONSTRAINT fk_pago_cuota_cuenta FOREIGN KEY (CBU_Origen) REFERENCES cuentas (CBU) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_pago_cuota_prestamo FOREIGN KEY (ID_Prestamo) REFERENCES prestamos (ID_Prestamo) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Table transferencias
DROP TABLE IF EXISTS transferencias;
CREATE TABLE transferencias (
  ID_Transferencia int(11) NOT NULL AUTO_INCREMENT,
  CBU_Origen varchar(22) NOT NULL,
  CBU_Destino varchar(22) NOT NULL,
  FechaTransferencia datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  Importe decimal(12, 2) NOT NULL,
  PRIMARY KEY (ID_Transferencia),
  UNIQUE KEY ID_Transferencia_UNIQUE (ID_Transferencia),
  CONSTRAINT fk_transferencia_cuenta_origen FOREIGN KEY (CBU_Origen) REFERENCES cuentas (CBU) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_transferencia_cuenta_destino FOREIGN KEY (CBU_Destino) REFERENCES cuentas (CBU) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Insertar países
INSERT INTO paises (Pais) VALUES
	('Argentina'),
	('Brasil'),
	('Chile'),
	('Colombia'),
	('Ecuador'),
	('Perú'),
	('Uruguay'),
	('Paraguay'),
    ('Estados Unidos'),
    ('España'),
    ('Francia'),
    ('Italia'),
    ('Alemania');
    
-- Insertar provincias ARGENTINAS 
INSERT INTO provincias (Provincia) VALUES
	('Buenos Aires'),
	('Córdoba'),
	('Santa Fe'),
	('Mendoza'),
	('Tucumán'),
	('Salta'),
	('Entre Ríos'),
	('Misiones'),
	('Chaco'),
	('Neuquén'),
    ('San Juan'),
    ('La Rioja'),
    ('Catamarca'),
    ('Tierra del Fuego');
    
-- Insertar Localidades ARGENTINAS
INSERT INTO localidades (ID_Provincia, Localidad)
VALUES
	(1, 'La Plata'),       -- Buenos Aires
    (1, 'Mar del Plata'),
    (1, 'Bahía Blanca'),
    (1, 'San Nicolás de los Arroyos'),
    (1, 'Tandil'),
	(1, 'Tigre'),
    (2, 'Córdoba Capital'),    -- Córdoba
    (2, 'Villa Carlos Paz'),
    (2, 'Río Cuarto'),
    (3, 'Rosario'),    -- Santa Fe
    (3, 'Santa Fe Capital'),
    (3, 'Rafaela'),
    (4, 'Mendoza Capital'),    -- Mendoza
    (4, 'San Rafael'),
    (4, 'Godoy Cruz'),
	(5, 'San Miguel de Tucumán'),    -- Tucumán
    (5, 'Yerba Buena'),
    (5, 'Tafí Viejo'),
	(6, 'Salta Capital'),    -- Salta
    (6, 'San Ramón de la Nueva Orán'),
    (6, 'Tartagal'),
    (7, 'Paraná'),    -- Entre Ríos
    (7, 'Concordia'),
    (7, 'Gualeguaychú'),
	(8, 'Posadas'),    -- Misiones
    (8, 'Puerto Iguazú'),
    (8, 'Eldorado'),
    (9, 'Resistencia'),    -- Chaco
    (9, 'Barranqueras'),
    (9, 'Sáenz Peña'),
    (10, 'Neuquén Capital'),-- Neuquén
    (10, 'Centenario');
    
 -- Insertar Usuario de Administradores
 INSERT INTO usuarios (Usuario, Contraseña, EsAdmin)
VALUES   ('ignacio', '1111', 1),
		 ('nicolas', '2222', 1),
		 ('juan', '3333', 1),
		 ('Luciano', '4444', 1),
		 ('jose', '5555', 1);
         
 -- Insertar Usuario de Clientes
 INSERT INTO usuarios (Usuario, Contraseña, EsAdmin)
VALUES   
		 ('joseCliente', '5555', 0),
		 ('anagonzalez', '5555', 0),
		 ('marialopez', '5555', 0),
		 ('carlosmartinez', '5555', 0),
         ('ignacioCliente', '5555', 0),
		 ('juanagonzalez', '5555', 0),
		 ('joselopez', '5555', 0),
		 ('pedromartinez', '5555', 0);


  -- Insertar Datos De Administradores
INSERT INTO InfoUsuarios (DNI, UsuarioCliente, CUIL, Nombre, Apellido, Sexo, ID_Nacionalidad, FechaNacimiento, ID_Provincia, ID_Localidad, Direccion, Mail, Telefono1, Telefono2, Estado)
VALUES
    (11111111, 'ignacio', '20222222220', 'Ignacio', 'Bee Bianco', 'Masculino', 1, '2000-01-01', 1, 6, 'Calle Falsa 123', 'ignaciobee@gmail.com',1156982563, null, 1),
    (22222222, 'nicolas', '30333333330', 'Nicolas', 'Caliari', 'Masculino', 1, '2000-05-15', 1, 6, 'Avenida Siempre Viva 456', 'nicolascaliari@gmail.com',1156232563, null, 1),
    (33333333, 'juan', '40444444440', 'Juan', 'Marín', 'Masculino', 1, '2000-11-20', 1, 6, 'Boulevard de los Sueños 789', 'juan_marin@gmail.com',1156984563, null, 1),
    (44444444, 'Luciano', '50555555550', 'Luciano', 'Grandjean', 'Masculino', 1, '2000-03-10', 1, 6, 'Ruta Imaginaria KM 10', 'lgrandjean@gmail.com',1156982533, null, 1),
    (95055710, 'jose', '23950557109', 'José', 'Pereira', 'Masculino', 8, '2000-10-14', 1, 6, 'Camino de los Recuerdos 111', 'jose_pereira@gmail.com',1156582563, null, 1);

  -- Insertar Datos De Clientes
INSERT INTO InfoUsuarios (DNI, UsuarioCliente, CUIL, Nombre, Apellido, Sexo, ID_Nacionalidad, FechaNacimiento, ID_Provincia, ID_Localidad, Direccion, Mail, Telefono1, Telefono2, Estado)
VALUES
    
	(95055711, 'joseCliente', '23950557100', 'José', 'Pereira', 'Masculino', 8, '2000-10-14', 1, 6, 'Camino de los Recuerdos 111', 'jose_pereira2@gmail.com',1156982563,1123982563, 1),
    (87654321, 'anagonzalez', '27876543210', 'Ana', 'González', 'Femenino', 3, '1995-07-22', 1, 2, 'Avenida Primavera 456', 'ana_gonzalez@gmail.com',1156982563, null, 1),
    (12345678, 'marialopez', '27123456780', 'María', 'López', 'Femenino', 1, '1988-03-18', 1, 3, 'Calle del Sol 789', 'maria_lopez@hotmail.com',1156982563,1157682563, 1),
    (55555555, 'carlosmartinez', '27555555550', 'Carlos', 'Martínez ', 'Masculino', 5, '1992-12-30', 1, 4, 'Plaza Principal 321', 'carlos_martinez@yahoo.com',1156982563, null, 1),
    (95055720, 'ignacioCliente', '23950557150', 'Ignacio', 'Bee Bianco (cliente)', 'Masculino', 8, '2000-10-14', 1, 6, 'Camino de los Recuerdos 111', 'jose_pereira2@gmail.com',1156982563,1123982563, 1),
    (87654330, 'juanagonzalez', '27876543215', 'Juana', 'González', 'Femenino', 3, '1995-07-22', 1, 2, 'Avenida Primavera 456', 'ana_gonzalez@gmail.com',1156982563, null, 1),
    (12345640, 'joselopez', '27123456790', 'Jose', 'López', 'Femenino', 1, '1988-03-18', 1, 3, 'Calle del Sol 789', 'maria_lopez@hotmail.com',1156982563,1157682563, 1),
    (55555550, 'pedromartinez', '27555555360', 'Pedro', 'Martínez ', 'Masculino', 5, '1992-12-30', 1, 4, 'Plaza Principal 321', 'carlos_martinez@yahoo.com',1156982563, null, 1);

-- Tipos de cuentas posibles:
-- • Caja de ahorro.
-- • Cuenta corriente. 
INSERT INTO tipodecuentas (Descripcion) VALUES
    ('Cuenta Corriente'),
    ('Caja de Ahorro');
 

-- Tipos de movimientos posibles:
-- • Alta de cuenta. 
-- • Alta de un préstamo. 
-- • Pago de préstamo.
-- • Transferencia. 
INSERT INTO tipodemovimientos (NombreTipoMovimiento) VALUES 
    ('Alta de cuenta'), 
    ('Alta Préstamo'), 
    ('Pago de préstamo'), 
    ('Transferencia');


 -- TRIGGER PARA GENERAR EL CBU EN BASE AL NUMERO DE CUENTA AUTO INCREMENTABLE 
DELIMITER //

CREATE TRIGGER generar_cbu BEFORE INSERT ON cuentas
FOR EACH ROW
BEGIN
    -- Para formar el cbu no contemple los digitos verificadores, para simplificar un poco
    DECLARE cbu_prefijo VARCHAR(8);
    DECLARE numero_cuenta VARCHAR(14);
	

    
    -- Código de banco (017) y código de sucursal (00001)
    SET cbu_prefijo = '01700001';
    
    -- Obtenemos el número de cuenta generado por AUTO_INCREMENT
    SET NEW.NumeroCuenta = (SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='cuentas');
    
    -- Convierte el número de cuenta a una cadena de 14 caracteres, rellenando con ceros a la izquierda 
    SET numero_cuenta = LPAD(NEW.NumeroCuenta, 14, '0');
    
    -- Construimos el CBU con 22 dígitos concatenando el prefijo y el número de cuenta
    SET NEW.CBU = CONCAT(cbu_prefijo, numero_cuenta);

    -- Incrementamos el AUTO_INCREMENT para la siguiente inserción
    SET @dummy = (SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='cuentas');
    
END//

-- TRIGGER PARA GENERAR MOVIMIENTO AL DAR DE ALTA UNA CUENTA
DELIMITER //

CREATE TRIGGER generar_movimiento_alta_cuenta AFTER INSERT ON cuentas
FOR EACH ROW
BEGIN
    DECLARE tipo_cuenta VARCHAR(50);
    
    -- Obtener la descripción del tipo de cuenta
    SELECT Descripcion INTO tipo_cuenta FROM tipodecuentas WHERE idTipoCuenta = NEW.idTipoCuenta;
    
    -- Insertar el movimiento correspondiente en la tabla movimientos
    INSERT INTO movimientos (CBU_Cliente, FechaMovimiento, Importe, SaldoPosterior, Detalle_Concepto, ID_TipoMovimiento)
    VALUES (NEW.CBU, NOW(), NEW.Saldo, NEW.Saldo, CONCAT('Alta de ', tipo_cuenta), 1);
END//

DELIMITER ;


  -- Asignamos cuentas a usuarios
INSERT INTO cuentas (DNI, idTipoCuenta, FechaCreacion)
VALUES   (95055711, 2, NOW()),
	     (95055711, 1, NOW()),
		 (55555555, 1, NOW()),
		 (55555555, 1, NOW()),
         (12345678, 2, NOW()),
	     (12345678, 1, NOW()),
	     (87654321, 1, NOW());
         
-- TRIGGER PARA ACTUALIZAR SALDOS Y GENERAR EL MOVIMIENTO CUANDO SE TRANSFIERE 
DELIMITER //
CREATE TRIGGER actualizar_saldos AFTER INSERT ON transferencias
FOR EACH ROW
BEGIN
    DECLARE saldo_origen DECIMAL(12, 2);
    DECLARE saldo_destino DECIMAL(12, 2);
        
    DECLARE saldo_posterior_origen DECIMAL(12, 2);
    DECLARE saldo_posterior_destino DECIMAL(12, 2);
    
    -- Obtener el saldo actual de la cuenta de origen y destino
    SELECT Saldo INTO saldo_origen FROM cuentas WHERE CBU = NEW.CBU_Origen;
    SELECT Saldo INTO saldo_destino FROM cuentas WHERE CBU = NEW.CBU_Destino;
    
    
    -- Calcular saldos posteriores
    SET saldo_posterior_origen = saldo_origen - NEW.Importe;
    SET saldo_posterior_destino = saldo_destino + NEW.Importe;
    
    -- Insertar el movimiento correspondiente en la tabla movimientos para la cuenta de origen
    INSERT INTO movimientos (CBU_Cliente, FechaMovimiento, Importe, SaldoPosterior, Detalle_Concepto, ID_TipoMovimiento)
    VALUES (NEW.CBU_Origen, NOW(), -NEW.Importe, saldo_posterior_origen, CONCAT('Transferencia hacia ', NEW.CBU_Destino), 4);
    
    -- Insertar el movimiento correspondiente en la tabla movimientos para la cuenta de destino
    INSERT INTO movimientos (CBU_Cliente, FechaMovimiento, Importe, SaldoPosterior, Detalle_Concepto, ID_TipoMovimiento)
    VALUES (NEW.CBU_Destino, NOW(), NEW.Importe, saldo_posterior_destino, CONCAT('Transferencia Desde ', NEW.CBU_Origen), 4);
    
    -- Actualizar saldo en la cuenta de origen
    UPDATE cuentas SET Saldo = saldo_posterior_origen WHERE CBU = NEW.CBU_Origen;
    
    -- Actualizar saldo en la cuenta de destino
    UPDATE cuentas SET Saldo = saldo_posterior_destino WHERE CBU = NEW.CBU_Destino;
    
END//

DELIMITER ;

         
-- Insertar transferencias
INSERT INTO transferencias (CBU_Origen, CBU_Destino, FechaTransferencia, Importe) VALUES
    ('0170000100000000000001', '0170000100000000000002', '2023-01-15 10:30:00', 5000.00),
    ('0170000100000000000002', '0170000100000000000001', '2023-02-20 14:45:00', 7500.00),
    ('0170000100000000000001', '0170000100000000000002', '2023-01-15 10:30:00', 2500.00),
    ('0170000100000000000001', '0170000100000000000002', '2023-01-15 10:30:00', 5000.00);



-- TRIGGER PARA GENERAR LAS CUOTAS CUANDO EL PRESTAMO ES ACEPTADO
DELIMITER //
CREATE TRIGGER agregar_cuotas
AFTER UPDATE ON prestamos
FOR EACH ROW
BEGIN
    DECLARE nroCuota INT DEFAULT 1;
    DECLARE intervalMeses INT;
    DECLARE fechaVencimiento DATE;
    DECLARE mesInicio INT;

    -- Verificar si el estado de la solicitud cambia de 'En espera' (2) a 'Aceptado' (1)
    IF OLD.EstadoSolicitud = 2 AND NEW.EstadoSolicitud = 1 THEN
        -- Calcular el intervalo de meses entre cada cuota
        SET intervalMeses = NEW.PlazoMeses / NEW.CantidadCuotas;
        
        -- Obtener el mes actual y el año actual de la aprobación del préstamo
        SET mesInicio = MONTH(CURRENT_DATE);
        
        -- Calcular el primer vencimiento el día 12 del próximo mes si el préstamo es mensual,
        -- o el 12 del mes correspondiente si las cuotas son bimestrales, trimestrales, etc.
        SET fechaVencimiento = MAKEDATE(YEAR(CURRENT_DATE), 1) + INTERVAL mesInicio + intervalMeses - 1 MONTH;
        SET fechaVencimiento = DATE_FORMAT(fechaVencimiento, '%Y-%m-12');
        
        -- Generar las cuotas
        WHILE nroCuota <= NEW.CantidadCuotas DO
            -- Insertar la cuota en la tabla pagoscuotas
            INSERT INTO pagoscuotas(ID_Prestamo, NroCuota, FechaVencimiento, ImportePago, EstadoCuota)
            VALUES (NEW.ID_Prestamo, nroCuota, fechaVencimiento, NEW.MontoCuota, 0);
            
            -- Calcular la fecha de vencimiento para la siguiente cuota
            SET fechaVencimiento = DATE_ADD(fechaVencimiento, INTERVAL intervalMeses MONTH);
            SET fechaVencimiento = DATE_FORMAT(fechaVencimiento, '%Y-%m-12');
            
            -- Incrementar el número de cuota
            SET nroCuota = nroCuota + 1;
        END WHILE;
    END IF;
END //

DELIMITER ;



-- TRIGGER PARA GENERAR EL MOVIMIENTO "ALTA PRESTAMO"
DELIMITER //

CREATE TRIGGER trg_prestamo_aprobado
AFTER UPDATE ON prestamos
FOR EACH ROW
BEGIN
		DECLARE saldo_anterior DECIMAL(12, 2);
        DECLARE saldo_posterior DECIMAL(12, 2);
        
        IF NEW.EstadoSolicitud = 1 AND OLD.EstadoSolicitud = 2 THEN
        
        -- Obtener el saldo actual de la cuenta
        SELECT Saldo INTO saldo_anterior FROM cuentas WHERE CBU = NEW.CBU_Deposito;
        
        -- Calcular el nuevo saldo después del movimiento
        SET saldo_posterior = saldo_anterior + NEW.ImportePedido;
        
        -- Insertar el movimiento en la tabla movimientos
        INSERT INTO movimientos (CBU_Cliente, FechaMovimiento, Importe, SaldoPosterior, Detalle_Concepto, ID_TipoMovimiento)
        VALUES (NEW.CBU_Deposito, NOW(), NEW.ImportePedido, saldo_posterior, 'Desembolso de préstamo aprobado', 2);
        
		-- Actualizar el saldo en la cuenta
       UPDATE cuentas SET Saldo = saldo_posterior WHERE CBU = NEW.CBU_Deposito;
    
    END IF;
END //

DELIMITER ;

INSERT INTO prestamos (DNI_Cliente, FechaSolicitud, ImportePedido, PlazoMeses, CantidadCuotas, ImportePagar, MontoCuota, EstadoSolicitud, EstadoPrestamo, CBU_Deposito) 
VALUES
    (95055711, '2023-06-10 10:00:00', 25000.00, 12, 6, 32500.00, 5416.67, 2, 0, '0170000100000000000001'), -- Bimestral 
    (95055711, '2023-07-20 14:30:00', 45000.00, 12, 12, 58500.00, 4875.00, 2, 0, '0170000100000000000002'),
    (55555555, '2023-07-20 14:30:00', 45000.00, 12, 4, 58500.00, 4875.00, 2, 0, '0170000100000000000003'),-- Trimestral
	(95055711, '2023-06-10 10:00:00', 25000.00, 6, 6, 32500.00, 5416.67, 2, 0, '0170000100000000000001'), -- Mensual 
    (55555555, '2023-08-25 09:45:00', 30000.00, 8, 8, 39000.00, 4875.00, 2, 0, '0170000100000000000003');
    

-- Aceptar Prestamos 1 , 3 Y 4 para generar sus Cuotas
UPDATE prestamos
SET EstadoSolicitud = 1
WHERE ID_Prestamo = 1;

UPDATE prestamos
SET EstadoSolicitud = 1
WHERE ID_Prestamo = 3;

UPDATE prestamos
SET EstadoSolicitud = 1
WHERE ID_Prestamo = 4;


-- TRIGGER PARA GENERAR EL MOVIMIENTO "PAGO DE PRÉSTAMO"
DELIMITER //

CREATE TRIGGER trg_pago_cuota
AFTER UPDATE ON pagoscuotas
FOR EACH ROW
BEGIN
    DECLARE saldo_anterior DECIMAL(12, 2);
    DECLARE saldo_posterior DECIMAL(12, 2);
    DECLARE num_cuota INT;
    DECLARE cuotas_faltantes INT;

    -- Verificar si la cuota ha sido marcada como pagada (EstadoCuota = 1)
    IF NEW.EstadoCuota = 1 AND OLD.EstadoCuota = 0 THEN
        -- Obtener el saldo actual de la cuenta
        SELECT Saldo INTO saldo_anterior FROM cuentas WHERE CBU = NEW.CBU_Origen;

        -- Calcular el nuevo saldo después del pago de la cuota
        SET saldo_posterior = saldo_anterior - NEW.ImportePago;

        -- Obtener el número de cuota abonado
        SET num_cuota = NEW.NroCuota;

        -- Obtener el total de cuotas del préstamo
        SELECT CantidadCuotas INTO cuotas_faltantes FROM prestamos WHERE ID_Prestamo = NEW.ID_Prestamo;

        -- Insertar el movimiento en la tabla movimientos
        INSERT INTO movimientos (CBU_Cliente, FechaMovimiento, Importe, SaldoPosterior, Detalle_Concepto, ID_TipoMovimiento)
        VALUES (NEW.CBU_Origen, NOW(), -NEW.ImportePago, saldo_posterior, CONCAT('Pago de cuota ', num_cuota, '/', cuotas_faltantes, ' del préstamo ID ', NEW.ID_Prestamo), 3);

        -- Actualizar el saldo en la cuenta
        UPDATE cuentas SET Saldo = saldo_posterior WHERE CBU = NEW.CBU_Origen;
    END IF;
END //

DELIMITER ;




-- Registrar los dos primeros pagos para generar su movimiento "Pago Prestamo" Usuario josecliente
UPDATE pagoscuotas
SET FechaPago = NOW(), 
    CBU_Origen = '0170000100000000000001', -- SE PAGA CON LA CUENTA NUMERO 1
    ImportePago = 5416.67, 
    EstadoCuota = 1 -- Marcamos la cuota como pagada
WHERE ID_Prestamo = 1 AND NroCuota = 1;

UPDATE pagoscuotas
SET FechaPago = NOW(),
    CBU_Origen = '0170000100000000000002', -- SE PAGA CON LA CUENTA NUMERO 2
    ImportePago = 5416.67,
    EstadoCuota = 1
WHERE ID_Prestamo = 1 AND NroCuota = 2;



-- Trigger para cambiar el estado del préstamo a "Pagado" cuando se pagan todas las cuotas
DELIMITER //

CREATE TRIGGER trg_prestamo_pagado
AFTER UPDATE ON pagoscuotas
FOR EACH ROW
BEGIN
    DECLARE total_cuotas INT;
    DECLARE cuotas_pagadas INT;

    -- Obtener el total de cuotas del préstamo
    SELECT CantidadCuotas INTO total_cuotas FROM prestamos WHERE ID_Prestamo = NEW.ID_Prestamo;

    -- Contar cuántas cuotas han sido pagadas
    SELECT COUNT(*) INTO cuotas_pagadas FROM pagoscuotas WHERE ID_Prestamo = NEW.ID_Prestamo AND EstadoCuota = 1;

    -- Si todas las cuotas han sido pagadas, cambiar el estado del préstamo a "Pagado"
    IF cuotas_pagadas = total_cuotas THEN
        UPDATE prestamos SET EstadoPrestamo = 1 WHERE ID_Prestamo = NEW.ID_Prestamo;
    END IF;
END //

DELIMITER ;




