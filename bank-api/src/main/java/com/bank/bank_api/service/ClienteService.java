package com.bank.bank_api.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.bank.bank_api.repository.ClienteRepository;
import com.bank.bank_api.model.Cliente;
import com.bank.bank_api.exception.ResourceNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listar() {
        return repo.findAll();
    }

    // OBTENER POR ID
    public Cliente obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    // GUARDAR
    public Cliente guardar(Cliente c) {
        return repo.save(c);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}