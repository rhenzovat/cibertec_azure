package com.cibertec.t2.repository;

import com.cibertec.t2.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    
    // Find alquileres with their detalles eagerly loaded
    @Query("SELECT DISTINCT a FROM Alquiler a LEFT JOIN FETCH a.detalles LEFT JOIN FETCH a.cliente")
    List<Alquiler> findAllWithDetalles();
    
    // Find alquileres by cliente
    List<Alquiler> findByCliente_IdCliente(Long clienteId);
} 