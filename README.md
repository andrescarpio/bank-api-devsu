# Bank API - Devsu Challenge

Aplicación fullstack para gestión de clientes, cuentas y movimientos bancarios.

---

## Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL

### Frontend
- Angular
- TypeScript

### Otros
- Postman (pruebas API)
- jsPDF (generación de reportes PDF)

---

## Estructura del proyecto

- bank-api/ -> Backend (Spring Boot)
- bank-frontend/ -> Frontend (Angular)
- database/ -> Script SQL
- BankAPI.postman_collection -> Colección Postman

---

## Configuración de la Base de Datos

1. Crear base de datos ejecutando:
database/BaseDatos.sql

2. Configurar conexión en:
bank-api/src/main/resources/application.properties

Ejemplo:
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=none

---

## Ejecución del Backend

``bash
cd bank-api
mvn spring-boot:run
Backend disponible en:
http://localhost:8080

---

## Ejecución del Frontend
cd bank-frontend
npm install
ng serve
Frontend disponible en:
http://localhost:4200

---

## Endpoints principales

### Clientes
- GET /clientes
- POST /clientes
- PUT /clientes/{id}
- DELETE /clientes/{id}

### Cuentas
- GET /cuentas
- POST /cuentas
- GET /cuentas/cliente/{clienteId}

### Movimientos
- POST /movimientos

### Reportes
- GET /movimientos/reportes?desde=YYYY-MM-DD&hasta=YYYY-MM-DD
- GET /movimientos/reportes?clienteId=1&desde=YYYY-MM-DD&hasta=YYYY-MM-DD

---

## Funcionalidades

- CRUD de clientes
- CRUD de cuentas
- Registro de movimientos (créditos y débitos)
- Validación de saldo disponible
- Límite diario de retiro (1000)
- Reporte por rango de fechas
- Filtro opcional por cliente
- Generación de reporte en PDF

---

## Postman

Se incluye colección para pruebas:
BankAPI.postman_collection

---

## Consideraciones técnicas
- Arquitectura basada en capas (Controller, Service, Repository)
- Uso de DTOs para transferencia de datos
- Manejo de excepciones personalizadas
- Validaciones de negocio en backend
- Frontend con componentes standalone

---

## Autor

Andres Carpio

---
