DROP DATABASE IF EXISTS bankdb;
CREATE DATABASE bankdb;
USE bankdb;

/* TABLA CLIENTE */
CREATE TABLE cliente (
  cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  genero VARCHAR(20),
  edad INT NOT NULL,
  identificacion VARCHAR(50),
  direccion VARCHAR(100),
  telefono VARCHAR(20),
  contrasena VARCHAR(100),
  estado BOOLEAN NOT NULL
);

/* TABLA CUENTA */
CREATE TABLE cuenta (
  cuenta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  numero_cuenta VARCHAR(50),
  tipo_cuenta VARCHAR(50) NOT NULL,
  saldo_inicial DECIMAL(10,2) NOT NULL,
  estado BOOLEAN NOT NULL,
  cliente_id BIGINT,

  CONSTRAINT fk_cuenta_cliente
  FOREIGN KEY (cliente_id)
  REFERENCES cliente(cliente_id)
  ON DELETE CASCADE
);

/* TABLA MOVIMIENTO */
CREATE TABLE movimiento (
  movimiento_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  tipo_movimiento VARCHAR(20),
  valor DECIMAL(10,2) NOT NULL,
  saldo DECIMAL(10,2) NOT NULL,
  cuenta_id BIGINT,

  CONSTRAINT fk_movimiento_cuenta
  FOREIGN KEY (cuenta_id)
  REFERENCES cuenta(cuenta_id)
  ON DELETE CASCADE
);