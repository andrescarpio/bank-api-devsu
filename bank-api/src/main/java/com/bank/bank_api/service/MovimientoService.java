package com.bank.bank_api.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bank.bank_api.repository.*;
import com.bank.bank_api.model.*;
import com.bank.bank_api.exception.*;

import java.util.*;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepo;

    @Autowired
    private CuentaRepository cuentaRepo;

    public Movimiento crear(String numeroCuenta, double valor) {

        Cuenta cuenta = cuentaRepo.findByNumeroCuenta(numeroCuenta);

        if (cuenta == null) {
            throw new ResourceNotFoundException("Cuenta no encontrada");
        }

        double saldoActual = cuenta.getSaldoInicial();

        if (valor < 0) {
            validarLimiteDiario(numeroCuenta, valor);
        }

        double nuevoSaldo = saldoActual + valor;

        if (nuevoSaldo < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Date());
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setTipoMovimiento(valor > 0 ? "CREDITO" : "DEBITO");
        movimiento.setCuenta(cuenta);

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepo.save(cuenta);

        return movimientoRepo.save(movimiento);
    }

    private void validarLimiteDiario(String numeroCuenta, double valor) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Date inicio = cal.getTime();
        Date fin = new Date();

        List<Movimiento> movimientos = movimientoRepo.findByCuentaNumeroCuentaAndFechaBetween(numeroCuenta, inicio,
                fin);

        double totalRetiros = movimientos.stream()
                .filter(m -> m.getValor() < 0)
                .mapToDouble(m -> Math.abs(m.getValor()))
                .sum();

        double nuevoRetiro = Math.abs(valor);

        if (totalRetiros + nuevoRetiro > 1000) {
            throw new BusinessException("Cupo diario excedido");
        }
    }
}