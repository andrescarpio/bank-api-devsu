package com.bank.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.bank_api.model.Movimiento;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

        List<Movimiento> findByCuentaClienteClienteIdAndFechaBetween(
                        Long clienteId, Date inicio, Date fin);

        List<Movimiento> findByCuentaNumeroCuentaAndFechaBetween(
                        String numeroCuenta, Date inicio, Date fin);

        List<Movimiento> findByFechaBetween(Date inicio, Date fin);
}