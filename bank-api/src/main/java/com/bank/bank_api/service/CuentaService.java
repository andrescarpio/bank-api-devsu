package com.bank.bank_api.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.bank.bank_api.repository.CuentaRepository;
import com.bank.bank_api.repository.ClienteRepository;
import com.bank.bank_api.model.Cuenta;
import com.bank.bank_api.model.Cliente;
import com.bank.bank_api.exception.ResourceNotFoundException;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository repo;

    @Autowired
    private ClienteRepository clienteRepo;

    // LISTAR TODAS
    public List<Cuenta> listar() {
        return repo.findAll();
    }

    // CREAR CUENTA
    public Cuenta crear(Cuenta cuenta, Long clienteId) {

        Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cuenta.setCliente(cliente);

        // VALIDACIÓN BÁSICA
        if (cuenta.getSaldoInicial() < 0) {
            throw new RuntimeException("Saldo inicial no puede ser negativo");
        }

        return repo.save(cuenta);
    }

    // OBTENER POR ID
    public Cuenta obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
    }

    // ACTUALIZAR
    public Cuenta actualizar(Long id, Cuenta cuenta) {

        Cuenta existente = obtener(id);

        existente.setNumeroCuenta(cuenta.getNumeroCuenta());
        existente.setTipoCuenta(cuenta.getTipoCuenta());
        existente.setEstado(cuenta.isEstado());

        return repo.save(existente);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // EXTRA (CLAVE PARA EL PROYECTO)
    public List<Cuenta> obtenerPorCliente(Long clienteId) {
        return repo.findByClienteClienteId(clienteId);
    }
}