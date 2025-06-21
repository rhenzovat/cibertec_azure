package com.cibertec.t2.service;

import com.cibertec.t2.model.Alquiler;
import com.cibertec.t2.model.DetalleAlquiler;
import com.cibertec.t2.model.Cliente;
import com.cibertec.t2.model.Pelicula;
import com.cibertec.t2.repository.AlquilerRepository;
import com.cibertec.t2.repository.ClienteRepository;
import com.cibertec.t2.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
@Transactional
public class AlquilerService {
    
    @Autowired
    private AlquilerRepository alquilerRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    public List<Alquiler> findAll() {
        return alquilerRepository.findAllWithDetalles();
    }
    
    public Optional<Alquiler> findById(Long id) {
        return alquilerRepository.findById(id);
    }
    
    public Alquiler save(Alquiler alquiler) {
        // Set fecha if not provided
        if (alquiler.getFecha() == null) {
            alquiler.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        
        // Set alquiler reference in detalles
        if (alquiler.getDetalles() != null) {
            for (DetalleAlquiler detalle : alquiler.getDetalles()) {
                detalle.setAlquiler(alquiler);
            }
        }
        
        return alquilerRepository.save(alquiler);
    }
    
    public void deleteById(Long id) {
        alquilerRepository.deleteById(id);
    }
    
    public Alquiler update(Long id, Alquiler alquilerDetails) {
        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);
        if (optionalAlquiler.isPresent()) {
            Alquiler alquiler = optionalAlquiler.get();
            alquiler.setCliente(alquilerDetails.getCliente());
            alquiler.setTotal(alquilerDetails.getTotal());
            alquiler.setEstado(alquilerDetails.getEstado());
            
            // Clear existing detalles first
            if (alquiler.getDetalles() != null) {
                alquiler.getDetalles().clear();
                alquilerRepository.flush(); // Force the clear to be persisted
            }
            
            // Add new detalles
            if (alquilerDetails.getDetalles() != null) {
                List<DetalleAlquiler> newDetalles = new ArrayList<>();
                for (DetalleAlquiler detalle : alquilerDetails.getDetalles()) {
                    DetalleAlquiler newDetalle = new DetalleAlquiler();
                    newDetalle.setCantidad(detalle.getCantidad());
                    newDetalle.setPelicula(detalle.getPelicula());
                    newDetalle.setAlquiler(alquiler);
                    newDetalles.add(newDetalle);
                }
                alquiler.setDetalles(newDetalles);
            }
            
            return alquilerRepository.save(alquiler);
        }
        return null;
    }
    
    public Alquiler createAlquiler(Long clienteId, String estado, List<Long> peliculaIds, List<Integer> cantidades) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new RuntimeException("Cliente no encontrado");
        }
        
        Alquiler alquiler = new Alquiler();
        alquiler.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        alquiler.setCliente(clienteOpt.get());
        alquiler.setEstado(estado);
        
        List<DetalleAlquiler> detalles = new ArrayList<>();
        double total = 0.0;
        
        for (int i = 0; i < peliculaIds.size(); i++) {
            Optional<Pelicula> peliculaOpt = peliculaRepository.findById(peliculaIds.get(i));
            if (peliculaOpt.isPresent()) {
                DetalleAlquiler detalle = new DetalleAlquiler();
                detalle.setPelicula(peliculaOpt.get());
                detalle.setCantidad(cantidades.get(i));
                detalle.setAlquiler(alquiler);
                detalles.add(detalle);
                total += detalle.getCantidad(); // Simple calculation, can be improved
            }
        }
        
        alquiler.setDetalles(detalles);
        alquiler.setTotal(total);
        
        return alquilerRepository.save(alquiler);
    }
} 