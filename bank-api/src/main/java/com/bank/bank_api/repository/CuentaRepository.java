package com.bank.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.bank_api.model.Cuenta;
import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    // Obtener cuentas por cliente
    List<Cuenta> findByClienteClienteId(Long clienteId);

    // Obtener cuentas por cuenta
    Cuenta findByNumeroCuenta(String numeroCuenta);

}