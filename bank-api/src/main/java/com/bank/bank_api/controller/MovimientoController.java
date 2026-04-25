package com.bank.bank_api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import com.bank.bank_api.service.MovimientoService;
import com.bank.bank_api.repository.MovimientoRepository;
import com.bank.bank_api.model.Movimiento;
import com.bank.bank_api.dto.*;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "http://localhost:4200")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @Autowired
    private MovimientoRepository repo;

    @PostMapping
    public MovimientoResponseDTO crear(@RequestBody MovimientoRequestDTO dto) {

        Movimiento mov = service.crear(dto.getNumeroCuenta(), dto.getValor());
        return toDTO(mov);
    }

    @GetMapping("/reportes")
    public List<ReporteDTO> reporte(
            @RequestParam(required = false) Long clienteId,
            @RequestParam String desde,
            @RequestParam String hasta) {

        Date inicio = java.sql.Date.valueOf(desde);
        Date fin = java.sql.Date.valueOf(hasta);

        List<Movimiento> movimientos;

        if (clienteId != null) {
            movimientos = repo.findByCuentaClienteClienteIdAndFechaBetween(clienteId, inicio, fin);
        } else {
            movimientos = repo.findByFechaBetween(inicio, fin);
        }

        return movimientos.stream().map(m -> {

            ReporteDTO dto = new ReporteDTO();
            dto.setFecha(m.getFecha());
            dto.setCliente(m.getCuenta().getCliente().getNombre());
            dto.setNumeroCuenta(m.getCuenta().getNumeroCuenta());
            dto.setTipoCuenta(m.getCuenta().getTipoCuenta());
            dto.setSaldoInicial(m.getCuenta().getSaldoInicial());
            dto.setEstado(m.getCuenta().isEstado());
            dto.setMovimiento(m.getValor());
            dto.setSaldoDisponible(m.getSaldo());

            return dto;

        }).collect(Collectors.toList());
    }

    private MovimientoResponseDTO toDTO(Movimiento m) {
        MovimientoResponseDTO dto = new MovimientoResponseDTO();

        dto.setFecha(m.getFecha());
        dto.setTipoMovimiento(m.getTipoMovimiento());
        dto.setValor(m.getValor());
        dto.setSaldo(m.getSaldo());

        if (m.getCuenta() != null) {
            dto.setNumeroCuenta(m.getCuenta().getNumeroCuenta());
        }

        return dto;
    }
}