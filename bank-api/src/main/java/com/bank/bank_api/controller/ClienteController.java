package com.bank.bank_api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import com.bank.bank_api.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
//import jakarta.validation.Valid;

import com.bank.bank_api.model.Cliente;
import com.bank.bank_api.dto.*;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(summary = "Listar clientes")
    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return service.listar().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Crear cliente")
    @PostMapping
    public ClienteResponseDTO crear(@RequestBody ClienteRequestDTO dto) {
        Cliente cliente = toEntity(dto);
        return toDTO(service.guardar(cliente));
    }

    @Operation(summary = "Obtener ID Cliente")
    @GetMapping("/{id}")
    public ClienteResponseDTO obtener(@PathVariable Long id) {
        return toDTO(service.obtener(id));
    }

    @Operation(summary = "Actualizar cliente")
    @PutMapping("/{id}")
    public ClienteResponseDTO actualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        Cliente cliente = toEntity(dto);
        cliente.setClienteId(id);
        return toDTO(service.guardar(cliente));
    }

    @Operation(summary = "Eliminar cliente")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @Operation(summary = "Cambiar estado del cliente")
    @PatchMapping("/{id}/estado")
    public ClienteResponseDTO cambiarEstado(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Cliente cliente = service.obtener(id);
        Boolean estado = body.get("estado");

        if (estado == null) {
            throw new RuntimeException("El campo 'estado' es obligatorio");
        }

        cliente.setEstado(estado);
        return toDTO(service.guardar(cliente));
    }

    // MAPEOS
    private Cliente toEntity(ClienteRequestDTO dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.getNombre());
        c.setGenero(dto.getGenero());
        c.setEdad(dto.getEdad());
        c.setIdentificacion(dto.getIdentificacion());
        c.setDireccion(dto.getDireccion());
        c.setTelefono(dto.getTelefono());
        c.setContrasena(dto.getContrasena());
        c.setEstado(dto.isEstado());
        return c;
    }

    private ClienteResponseDTO toDTO(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();

        dto.setClienteId(c.getClienteId());

        dto.setNombre(c.getNombre());
        dto.setGenero(c.getGenero());
        dto.setEdad(c.getEdad());
        dto.setIdentificacion(c.getIdentificacion());
        dto.setDireccion(c.getDireccion());
        dto.setTelefono(c.getTelefono());
        dto.setEstado(c.isEstado());

        return dto;
    }
}