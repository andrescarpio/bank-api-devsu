package com.bank.bank_api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import com.bank.bank_api.service.CuentaService;
import com.bank.bank_api.model.Cuenta;
import com.bank.bank_api.dto.*;
import com.bank.bank_api.exception.BusinessException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "http://localhost:4200")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @Operation(summary = "Listar todas las cuentas")
    @GetMapping
    public List<CuentaResponseDTO> listar() {
        return service.listar().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Obtener cuenta por id")
    @GetMapping("/{id}")
    public CuentaResponseDTO obtener(@PathVariable Long id) {
        return toDTO(service.obtener(id));
    }

    @Operation(summary = "Obtener cuentas por cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<CuentaResponseDTO> obtenerPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Crear cuenta")
    @PostMapping
    public CuentaResponseDTO crear(@RequestBody CuentaRequestDTO dto) {
        // VALIDACIONES
        if (dto.getTipoCuenta() == null || dto.getTipoCuenta().isEmpty()) {
            throw new BusinessException("Tipo de cuenta es obligatorio");
        }

        if (dto.getClienteId() == null) {
            throw new BusinessException("Cliente es obligatorio");
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.isEstado());

        return toDTO(service.crear(cuenta, dto.getClienteId()));
    }

    @Operation(summary = "Actualizar cuenta")
    @PutMapping("/{id}")
    public CuentaResponseDTO actualizar(@PathVariable Long id, @RequestBody CuentaRequestDTO dto) {

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setEstado(dto.isEstado());

        return toDTO(service.actualizar(id, cuenta));
    }

    @Operation(summary = "Eliminar cuenta")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    private CuentaResponseDTO toDTO(Cuenta c) {
        CuentaResponseDTO dto = new CuentaResponseDTO();

        dto.setCuentaId(c.getCuentaId());
        dto.setNumeroCuenta(c.getNumeroCuenta());
        dto.setTipoCuenta(c.getTipoCuenta());
        dto.setSaldoInicial(c.getSaldoInicial());
        dto.setEstado(c.isEstado());

        if (c.getCliente() != null) {
            dto.setClienteId(c.getCliente().getClienteId());
            dto.setClienteNombre(c.getCliente().getNombre());
        }

        return dto;
    }

}