package com.cibertec.t2.controller;

import com.cibertec.t2.model.Pelicula;
import com.cibertec.t2.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {
    
    @Autowired
    private PeliculaService peliculaService;
    
    @GetMapping
    public String listarPeliculas(Model model) {
        List<Pelicula> peliculas = peliculaService.findAll();
        model.addAttribute("peliculas", peliculas);
        return "peliculas";
    }
    
    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula, RedirectAttributes redirectAttributes) {
        try {
            peliculaService.save(pelicula);
            redirectAttributes.addFlashAttribute("success", "Película guardada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar película: " + e.getMessage());
        }
        return "redirect:/peliculas";
    }
    
    @PostMapping("/actualizar")
    public String actualizarPelicula(@RequestParam Long id, @ModelAttribute Pelicula pelicula, RedirectAttributes redirectAttributes) {
        try {
            Pelicula updated = peliculaService.update(id, pelicula);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Película actualizada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Película no encontrada");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar película: " + e.getMessage());
        }
        return "redirect:/peliculas";
    }
    
    @PostMapping("/eliminar")
    public String eliminarPelicula(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            peliculaService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Película eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar película: " + e.getMessage());
        }
        return "redirect:/peliculas";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Pelicula> obtenerPelicula(@PathVariable Long id) {
        return peliculaService.findById(id);
    }
} 