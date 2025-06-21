package com.cibertec.t2.service;

import com.cibertec.t2.model.Pelicula;
import com.cibertec.t2.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeliculaService {
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }
    
    public Optional<Pelicula> findById(Long id) {
        return peliculaRepository.findById(id);
    }
    
    public Pelicula save(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }
    
    public void deleteById(Long id) {
        peliculaRepository.deleteById(id);
    }
    
    public Pelicula update(Long id, Pelicula peliculaDetails) {
        Optional<Pelicula> optionalPelicula = peliculaRepository.findById(id);
        if (optionalPelicula.isPresent()) {
            Pelicula pelicula = optionalPelicula.get();
            pelicula.setTitulo(peliculaDetails.getTitulo());
            pelicula.setGenero(peliculaDetails.getGenero());
            pelicula.setStock(peliculaDetails.getStock());
            return peliculaRepository.save(pelicula);
        }
        return null;
    }
} 