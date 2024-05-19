-- Ethan Jared Alberto Juarez Pinto
-- Proyecto Kinalitos Express
-- 2020269

-- ----------------------- DDL --------------------------------


drop database if exists DB_KinalitosExpress;

create database if not exists DB_KinalitosExpress;

use  DB_KinalitosExpress;
set global time_zone = "-6:00";

create table TipoProducto(
	codigoTipoProducto int not null,
    descripcion varchar(45) not null,
    primary key PK_codigoTipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date not null,
    descripcion varchar(60) not null,
    totalDocumento decimal (10, 2) not null,
    primary key PK_numeroDocumento(numeroDocumento)
);

create table Clientes(
	clienteID int not null,
    nitClientes varchar (10) not null,
    nombreClientes varchar (50),
    apellidoClientes varchar (50),
    direccionClientes varchar (150),
    telefonoClientes varchar (10),
    correoClientes varchar (45),
    primary key PK_clienteID (clienteID)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
    nobreCargo varchar (45),
    descripcionCargo varchar (45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table Proveedores(
	codigoProveedor int not null ,
    nitProveedores varchar (10) not null,
    nombresProveedores varchar (60) not null,
    apellidosProveedores varchar (60) not null,
    direccionProveedor varchar (150) not null,
    razonSocial varchar (60) not null,
    contactoPrincipal varchar (100) not null,
    paginaWeb varchar (50) not null,
    primary key PK_codigoProveedor(codigoProveedor)
);

create table TelefonoProveedor(
    codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8) not null,
    numeroSecundario varchar(8) not null,
    observaciones varchar(45) not null,
    codigoProveedor int not null,
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_Proveedores_TelefonoProveedor foreign key (codigoProveedor)
        references Proveedores(codigoProveedor)
);

create table EmailProveedores (
    codigoEmailProveedor int not null ,
    emailProveedor varchar(50) not null,
    descripcion varchar(100) not null,
	codigoProveedor int not null,
    primary key PK_codigEmailProveedor(codigoEmailProveedor),
    constraint FK_Proveedores_EmailProveedores foreign key (codigoProveedor) 
		references Proveedores(codigoProveedor)
);

create table Productos(
	codigoProducto varchar (15) not null,
    descripcionProducto varchar (45) not null,
	precioUitario decimal (10,2) not null,
    precioDocena decimal (10, 2) not null,
    precioPorMayor decimal (10,2) not null,
    imagenProducto varchar (45),
    existencia int not null,
    codigoTipoProducto int not null,
    codigoProveedor int not null, 
    primary key PK_codigoProducto (codigoProducto),
    constraint FK_TipoProducto_Productos foreign key (codigoTipoProducto)
		references TipoProducto (codigoTipoProducto),
	constraint FK_Proveedores_Productos foreign key (codigoProveedor) 
		references Proveedores(codigoProveedor)
);

create table detalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal (10,2) not null,
    cantidad int not null,
    codigoProducto varchar(45) not null,
    numeroDocumento int not null,
    primary key PK_codigoDetalleCompra (codigoDetalleCompra),
    constraint FK_Productos_DetalleCompra foreign key (codigoProducto)
		references Productos (codigoProducto),
	constraint FK_Compras_DetalleCompra foreign key(numeroDocumento)
		references Compras (numeroDocumento)
);

create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar (50) not null,
    apellidosEmpleado varchar(50)not null,
    sueldo decimal (10,2)not null,
    direccion varchar (150)not null,
    turno varchar (15)not null,
    codigoCargoEmpleado int not null,
	primary key PK_codigoEmpleado(codigoEmpleado),
	constraint F_CargoEmpleado_Empleados foreign key (codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);

create table Factura (
	numeroFactura int not null ,
    estado varchar (50) not null,
    totalFactura decimal (10,2)not null,
    fechaFactura varchar (45) not null,
    clienteID int not null,
    codigoEmpleado int not null,
    primary key PK_numerFactura (numeroFactura),
    constraint FK_Cliente_Factura foreign key (clienteID)
		references Clientes (clienteID),
	constraint FK_Empleados_Factura foreign key (codigoEmpleado)
		references Empleados (codigoEmpleado)
);

create table DetalleFactura (
	codigoDetalleFactura int not null ,
    precioUnitario decimal (10,2),
    cantidad int not null,
    numeroFactura int not null,
    codigoProducto varchar (15) not null,
    primary key PK_codigoDetalleFactura (codigoDetalleFactura),
    constraint FK_Factura_DetalleFactura foreign key (numeroFactura)
		references Factura (numeroFactura),
	constraint FK_Productos_DetalleFactura foreign key (codigoProducto)
		references Productos (codigoProducto)
);



-- --------------------------------- DML -------------------

-- ---------------------------Clientes-------------------------------------

Delimiter $$
	Create procedure sp_AgregarClientes(in clienteID int, in nombreClientes varchar(50), in apellidoClientes varchar(50), in nitClientes varchar(10),
    in telefonoClientes varchar (15), in direccionClientes varchar(50), in correoClientes varchar(45))
			Begin
				Insert into Clientes (clienteID, nombreClientes, apellidoClientes, nitClientes, telefonoClientes, direccionClientes, correoClientes ) values 
					(clienteID, nombreClientes, apellidoClientes, nitClientes, telefonoClientes, direccionClientes, correoClientes);
		End $$
Delimiter ;

call sp_AgregarClientes(001, 'Luis Pedro', 'Hernandez Garcia','114006350', '12345678', '13-54 zona 7 colonia Landivar', 'kinal@kinal.edu.gt');
call sp_AgregarClientes(002, 'Jose Carlos', 'Cortez Carrera','53308239',  '12345678', '13-54 zona 7 colonia Landivar', 'kinal@kinal.edu.gt');

Delimiter $$
    Create procedure sp_ListarClientes()
		Begin
        
			Select 
				C.clienteID, 
                C.nombreClientes, 
                C.apellidoClientes,
                C.nitClientes,
                C.telefonoClientes,
                C.direccionClientes,
				C.correoClientes
			From Clientes C;
            
		End$$
    
Delimiter ;	

call sp_ListarClientes();

Delimiter $$
	Create procedure sp_BuscarClientes(in cliId int)
		Begin
			Select C.clienteID, 
				C.nombreClientes, 
                C.apellidoClientes, 
                C.nitClientes,
                C.telefonoClientes,
                C.direccionClientes,
                C.correoClientes
            From Clientes C 
            where clienteID = cliId;
        End $$
Delimiter ;

call sp_BuscarClientes(1);

Delimiter $$
	Create procedure sp_EliminarClientes (in cliID int)
		Begin 
			Delete from Clientes
				where clienteID = cliID;
		End $$
Delimiter ;

call sp_EliminarClientes(1);
call sp_ListarClientes();

Delimiter $$
	Create procedure sp_EditarClientes(in cliID int, in nomClientes varchar(50), in apeClientes varchar(50), 
        in nitCl varchar (10), in teleClientes varchar(15), in direClientes varchar(50), in corrClientes varchar(45))
			Begin
				Update Clientes C
					set C.nombreClientes = nomClientes, 
                    C.apellidoClientes = apeClientes,
                    C.nitClientes = nitCl,
                    C.telefonoClientes = teleClientes, 
                    C.direccionClientes = direClientes,
                    C.correoClientes = corrClientes
                    where cliID = clienteID ;
			End $$
Delimiter ;

call sp_EditarClientes(2, 'Jose Carlos Efrain', 'Cortez Carrera', '53308239', '12345678','13-54 zona 7 colonia Landivar', 'kinal@edu.gt' );
call sp_ListarClientes();

DELIMITER $$
CREATE PROCEDURE sp_actualizarClientes(in codigoCliente int, in nitClienteActualizado varchar(10), in nombreClienteActualizado varchar(50), in apellidoClienteActualizado varchar (50), in direccionClienteActualizado varchar(150), in telefonoClienteActualizado varchar(45), in correoClienteActualizado varchar(45))
BEGIN

	UPDATE Cliente
    SET nitCliente = nitClienteActualizado, nombreCliente = nombreClienteActualizado, apellidoCliente = apellidoClienteActualizado, direccionCliente = direccionClienteActualizado, telefonoCliente = telefonoClienteActualizado, correoCliente = correoClienteActualizado
    WHERE codigoCliente = codigoCliente;

END$$
DELIMITER ;

-- ------------------------------ TipoProducto ------------------------------- 

DELIMITER $$
CREATE PROCEDURE sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(15))
BEGIN

	INSERT INTO TipoProducto(codigoTipoProducto,descripcion)
		VALUES(codigoTipoProducto,descripcion);

END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_listarTipoProducto()
BEGIN

	SELECT * FROM TipoProducto;

END$$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_actualizarTipoProducto(
    IN p_codigoTipoProducto INT,
    IN p_nuevaDescripcion VARCHAR(45)
)
BEGIN
    UPDATE TipoProducto
    SET descripcion = p_nuevaDescripcion
    WHERE codigoTipoProducto = p_codigoTipoProducto;
END$$

DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminarTipoProducto(in codigoTipoProducto int)
BEGIN

	DELETE FROM TipoProducto
    WHERE codigoTipoProducto = codigoTipoProducto;

END$$
DELIMITER ;

CALL sp_eliminarTipoProducto(1);

DELIMITER $$


-- --------------------------------- Compras -----------------------------------------

CREATE PROCEDURE sp_agregarCompras(
    IN numeroDocumento INT,
    IN fechaDocumento DATE,
    IN descripcion VARCHAR(60),
    IN totalDocumento DECIMAL(10,2)
)
BEGIN
    INSERT INTO Compras(numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    VALUES(numeroDocumento, fechaDocumento, descripcion, totalDocumento);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_listarCompras()
BEGIN
    SELECT * FROM Compras;
END$$
DELIMITER ;

CALL sp_listarCompras();

DELIMITER $$
CREATE PROCEDURE sp_actualizarCompra(
    IN numeroDocumento INT,
    IN nuevaFechaDocumento DATE,
    IN nuevaDescripcion VARCHAR(60),
    IN nuevoTotalDocumento DECIMAL(10,2)
)
BEGIN
    UPDATE Compras
    SET fechaDocumento = nuevaFechaDocumento,
        descripcion = nuevaDescripcion,
        totalDocumento = nuevoTotalDocumento
    WHERE numeroDocumento = numeroDocumento;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminarCompra(
    IN numeroDocumento INT
)
BEGIN
    DELETE FROM Compras
    WHERE numeroDocumento = numeroDocumento;
END$$
DELIMITER ;

CALL sp_eliminarCompra(1234);

DELIMITER $$


-- -------------------------------------- CargoEmpleado ------------------- 


CREATE PROCEDURE sp_AgregarCargoEmpleado(
    IN p_codigoCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(45),
    IN p_descripcionCargo VARCHAR(45)
)
BEGIN
    INSERT INTO CargoEmpleado(codigoCargoEmpleado, nombreCargo, descripcionCargo)
    VALUES(p_codigoCargoEmpleado, p_nombreCargo, p_descripcionCargo);
END$$

CREATE PROCEDURE sp_listarCargoEmpleado()
BEGIN
    SELECT * FROM CargoEmpleado;
END$$

CALL sp_listarCargoEmpleado();

CREATE PROCEDURE sp_actualizarCargoEmpleado(
    IN p_codigoCargoEmpleado INT,
    IN p_nuevoNombreCargo VARCHAR(45),
    IN p_nuevaDescripcionCargo VARCHAR(45)
)
BEGIN
    UPDATE CargoEmpleado
    SET nombreCargo = p_nuevoNombreCargo,
        descripcionCargo = p_nuevaDescripcionCargo
    WHERE codigoCargoEmpleado = p_codigoCargoEmpleado;
END$$

CREATE PROCEDURE sp_eliminarCargoEmpleado(
    IN p_codigoCargoEmpleado INT
)
BEGIN
    DELETE FROM CargoEmpleado
    WHERE codigoCargoEmpleado = p_codigoCargoEmpleado;
END$$
DELIMITER ;

CALL sp_eliminarCargoEmpleado(1234);

DELIMITER $$

-- --------------------------------  Proveedor --------------------------

CREATE PROCEDURE sp_crearProveedor(
    IN p_codigoProveedor INT,
    IN p_nitProveedor VARCHAR(10),
    IN p_nombreProveedor VARCHAR(60),
    IN p_apellidosProveedor VARCHAR(60),
    IN p_direccionProveedor VARCHAR(150),
    IN p_razonSocial VARCHAR(60),
    IN p_contactoPrincipal VARCHAR(100),
    IN p_paginaWeb VARCHAR(50)
)
BEGIN
    INSERT INTO Proveedores(codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    VALUES(p_codigoProveedor, p_nitProveedor, p_nombreProveedor, p_apellidosProveedor, p_direccionProveedor, p_razonSocial, p_contactoPrincipal, p_paginaWeb);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_listarProveedores()
BEGIN
    SELECT * FROM Proveedores;
END$$
DELIMITER ;

CALL sp_listarProveedores();

DELIMITER $$

CREATE PROCEDURE sp_actualizarProveedor(
    IN p_codigoProveedor INT,
    IN p_nitProveedor VARCHAR(10),
    IN p_nombreProveedor VARCHAR(60),
    IN p_apellidosProveedor VARCHAR(60),
    IN p_direccionProveedor VARCHAR(150),
    IN p_razonSocial VARCHAR(60),
    IN p_contactoPrincipal VARCHAR(100),
    IN p_paginaWeb VARCHAR(50)
)
BEGIN
    UPDATE Proveedores
    SET nitProveedor = p_nitProveedor,
        nombreProveedor = p_nombreProveedor,
        apellidosProveedor = p_apellidosProveedor,
        direccionProveedor = p_direccionProveedor,
        razonSocial = p_razonSocial,
        contactoPrincipal = p_contactoPrincipal,
        paginaWeb = p_paginaWeb
    WHERE codigoProveedor = p_codigoProveedor;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminarProveedor(
    IN p_codigoProveedor INT
)
BEGIN
    DELETE FROM Proveedores
    WHERE codigoProveedor = p_codigoProveedor;
END$$

DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_crearTelefonoProveedor(
    IN p_codigoTelefonoProveedor INT,
    IN p_numeroPrincipal VARCHAR(8),
    IN p_numeroSecundario VARCHAR(8),
    IN p_observaciones VARCHAR(45),
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO TelefonoProveedor(codigoTelefonoProveedor, numeroPincipal, numeroSecundario, observaciones, codigoProveedor)
    VALUES(p_codigoTelefonoProveedor, p_numeroPrincipal, p_numeroSecundario, p_observaciones, p_codigoProveedor);
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_listarTelefonosProveedor()
BEGIN
    SELECT * FROM TelefonoProveedor;
END$$
DELIMITER ;

CALL sp_listarTelefonosProveedor();

DELIMITER $$
CREATE PROCEDURE sp_actualizarTelefonoProveedor(
    IN p_codigoTelefonoProveedor INT,
    IN p_nuevoNumeroPrincipal VARCHAR(8),
    IN p_nuevoNumeroSecundario VARCHAR(8),
    IN p_nuevasObservaciones VARCHAR(45),
    IN p_codigoProveedor INT
)
BEGIN
    UPDATE TelefonoProveedor
    SET numeroPincipal = p_nuevoNumeroPrincipal,
        numeroSecundario = p_nuevoNumeroSecundario,
        observaciones = p_nuevasObservaciones
    WHERE codigoTelefonoProveedor = p_codigoTelefonoProveedor
        AND codigoProveedor = p_codigoProveedor;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminarTelefonoProveedor(
    IN codigoTelefonoProveedor INT
)
BEGIN
    DELETE FROM TelefonoProveedor
    WHERE codigoTelefonoProveedor = codigoTelefonoProveedor;
END$$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_crearEmailProveedores(
    IN p_codigoEmailProveedor INT,
    IN p_emailProveedor VARCHAR(50),
    IN p_descripcion VARCHAR(100),
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO EmailProveedor(codigoEmailProveedor, emailproveedor, descripcion, codigoProveedor)
    VALUES(p_codigoEmailProveedor, p_emailProveedor, p_descripcion, p_codigoProveedor);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_listarEmailProveedores()
BEGIN
    SELECT * FROM EmailProveedores;
    
END$$
DELIMITER ;

CALL sp_listarEmailProveedores();

DELIMITER $$
CREATE PROCEDURE sp_actualizarEmailProveedores(
    IN p_codigoEmailProveedor INT,
    IN p_nuevoEmailProveedor VARCHAR(50),
    IN p_nuevaDescripcion VARCHAR(100),
    IN p_codigoProveedor INT
)
BEGIN
    UPDATE EmailProveedores
    SET emailproveedor = p_nuevoEmailProveedor,
        descripcion = p_nuevaDescripcion
    WHERE codigoEmailProveedor = p_codigoEmailProveedor
        AND codigoProveedor = p_codigoProveedor;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_eliminarEmailProveedor(
    IN p_codigoEmailProveedor INT,
    IN p_codigoProveedor INT
)
BEGIN
    DELETE FROM EmailProveedores
    WHERE codigoEmailProveedor = p_codigoEmailProveedor
        AND codigoProveedor = p_codigoProveedor;
END$$
DELIMITER ;

-- CRUD de Productos

DELIMITER $$

CREATE PROCEDURE sp_crearProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_imagenProducto VARCHAR(45),
    IN p_existencia INT,
    IN p_codigoTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_imagenProducto, p_existencia, p_codigoTipoProducto, p_codigoProveedor);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_listarProductos()
BEGIN
    SELECT * FROM Productos;
END$$
DELIMITER ;

CALL sp_listarProductos();

DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaImagenProducto VARCHAR(45),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        imagenProducto = p_nuevaImagenProducto,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;


CREATE PROCEDURE sp_eliminarProducto(
    IN codigoProducto VARCHAR(15)
)
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = codigoProducto
END$$

DELIMITER ;


-- CRUD de DetalleCompra 
DELIMITER $$

CREATE PROCEDURE sp_crearDetalleCompra(
    IN p_codigoDetalleCompra INT,
    IN p_costoUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_codigoProducto VARCHAR(15),
    IN p_numeroDocumento INT
)
BEGIN
    INSERT INTO DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
    VALUES(p_codigoDetalleCompra, p_costoUnitario, p_cantidad, p_codigoProducto, p_numeroDocumento);
END$$

CREATE PROCEDURE sp_listarDetallesCompra()
BEGIN
    SELECT * FROM DetalleCompra;
END$$

CREATE PROCEDURE sp_actualizarDetalleCompra(
    IN p_codigoDetalleCompra INT,
    IN p_nuevoCostoUnitario DECIMAL(10,2),
    IN p_nuevaCantidad INT,
    IN p_nuevoCodigoProducto VARCHAR(15),
    IN p_nuevoNumeroDocumento INT
)
BEGIN
    UPDATE DetalleCompra
    SET costoUnitario = p_nuevoCostoUnitario,
        cantidad = p_nuevaCantidad,
        codigoProducto = p_nuevoCodigoProducto,
        numeroDocumento = p_nuevoNumeroDocumento
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END$$

CREATE PROCEDURE sp_eliminarDetalleCompra(
    IN p_codigoDetalleCompra INT
)
BEGIN
    DELETE FROM DetalleCompra
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END$$

DELIMITER ;

-- CRUD de Empleados

DELIMITER $$

CREATE PROCEDURE sp_crearEmpleado(
    IN p_codigoEmpleado INT,
    IN p_nombresEmpleado VARCHAR(50),
    IN p_apellidosEmpleado VARCHAR(50),
    IN p_sueldo DECIMAL(10,2),
    IN p_direccion VARCHAR(150),
    IN p_turno VARCHAR(15),
    IN p_codigoCargoEmpleado INT
)
BEGIN
    INSERT INTO Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    VALUES(p_codigoEmpleado, p_nombresEmpleado, p_apellidosEmpleado, p_sueldo, p_direccion, p_turno, p_codigoCargoEmpleado);
END$$

CREATE PROCEDURE sp_listarEmpleados()
BEGIN
    SELECT * FROM Empleados;
END$$

CREATE PROCEDURE sp_actualizarEmpleado(
    IN p_codigoEmpleado INT,
    IN p_nuevosNombresEmpleado VARCHAR(50),
    IN p_nuevosApellidosEmpleado VARCHAR(50),
    IN p_nuevoSueldo DECIMAL(10,2),
    IN p_nuevaDireccion VARCHAR(150),
    IN p_nuevoTurno VARCHAR(15),
    IN p_nuevoCodigoCargoEmpleado INT
)
BEGIN
    UPDATE Empleados
    SET nombresEmpleado = p_nuevosNombresEmpleado,
        apellidosEmpleado = p_nuevosApellidosEmpleado,
        sueldo = p_nuevoSueldo,
        direccion = p_nuevaDireccion,
        turno = p_nuevoTurno,
        codigoCargoEmpleado = p_nuevoCodigoCargoEmpleado
    WHERE codigoEmpleado = p_codigoEmpleado;
END$$

CREATE PROCEDURE sp_eliminarEmpleado(
    IN p_codigoEmpleado INT
)
BEGIN
    DELETE FROM Empleados
    WHERE codigoEmpleado = p_codigoEmpleado;
END$$

DELIMITER ;

-- CRUD de Factura
DELIMITER $$

CREATE PROCEDURE sp_crearFactura(
    IN p_numeroDeFactura INT,
    IN p_estado VARCHAR(50),
    IN p_totalFactura DECIMAL(10,2),
    IN p_fechaFactura VARCHAR(45),
    IN p_codigoCliente INT,
    IN p_codigoEmpleado INT
)
BEGIN
    INSERT INTO Factura(numeroDeFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado)
    VALUES(p_numeroDeFactura, p_estado, p_totalFactura, p_fechaFactura, p_codigoCliente, p_codigoEmpleado);
END$$

CREATE PROCEDURE sp_listarFacturas()
BEGIN
    SELECT * FROM Factura;
END$$

CREATE PROCEDURE sp_actualizarFactura(
    IN p_numeroDeFactura INT,
    IN p_nuevoEstado VARCHAR(50),
    IN p_nuevoTotalFactura DECIMAL(10,2),
    IN p_nuevaFechaFactura VARCHAR(45),
    IN p_nuevoCodigoCliente INT,
    IN p_nuevoCodigoEmpleado INT
)
BEGIN
    UPDATE Factura
    SET estado = p_nuevoEstado,
        totalFactura = p_nuevoTotalFactura,
        fechaFactura = p_nuevaFechaFactura,
        codigoCliente = p_nuevoCodigoCliente,
        codigoEmpleado = p_nuevoCodigoEmpleado
    WHERE numeroDeFactura = p_numeroDeFactura;
END$$

CREATE PROCEDURE sp_eliminarFactura(
    IN p_numeroDeFactura INT
)
BEGIN
    DELETE FROM Factura
    WHERE numeroDeFactura = p_numeroDeFactura;
END$$

DELIMITER ;

-- CRUD de DetalleFactura 
DELIMITER $$

CREATE PROCEDURE sp_crearDetalleFactura(
    IN p_codigoDetalleFactura INT,
    IN p_precioUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_numeroDeFactura INT,
    IN p_codigoProducto VARCHAR(15)
)
BEGIN
    INSERT INTO DetalleFactura(codigoDetalleFactura, precioUnitario, cantidad, numeroDeFactura, codigoProducto)
    VALUES(p_codigoDetalleFactura, p_precioUnitario, p_cantidad, p_numeroDeFactura, p_codigoProducto);
END$$

CREATE PROCEDURE sp_listarDetallesFactura()
BEGIN
    SELECT * FROM DetalleFactura;
END$$

CREATE PROCEDURE sp_actualizarDetalleFactura(
    IN p_codigoDetalleFactura INT,
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevaCantidad INT,
    IN p_nuevoNumeroDeFactura INT,
    IN p_nuevoCodigoProducto VARCHAR(15)
)
BEGIN
    UPDATE DetalleFactura
    SET precioUnitario = p_nuevoPrecioUnitario,
        cantidad = p_nuevaCantidad,
        numeroDeFactura = p_nuevoNumeroDeFactura,
        codigoProducto = p_nuevoCodigoProducto
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END$$

CREATE PROCEDURE sp_eliminarDetalleFactura(
    IN p_codigoDetalleFactura INT
)
BEGIN
    DELETE FROM DetalleFactura
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END$$

DELIMITER ;