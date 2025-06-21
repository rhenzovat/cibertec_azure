package com.cibertec.t2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "peliculas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pelicula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;
    
    @NotNull
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;
    
    @NotNull
    @Column(name = "genero", length = 50, nullable = false)
    private String genero;
    
    @NotNull
    @Min(0)
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    // Constructors
    public Pelicula() {}
    
    public Pelicula(String titulo, String genero, Integer stock) {
        this.titulo = titulo;
        this.genero = genero;
        this.stock = stock;
    }
    
    // Getters and Setters
    public Long getIdPelicula() {
        return idPelicula;
    }
    
    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
} 