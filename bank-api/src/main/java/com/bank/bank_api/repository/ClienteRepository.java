package com.bank.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.bank_api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}