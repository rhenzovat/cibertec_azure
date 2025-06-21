package com.cibertec.t2.repository;

import com.cibertec.t2.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // Additional custom queries can be added here if needed
} 