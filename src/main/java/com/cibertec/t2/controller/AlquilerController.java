package com.cibertec.t2.controller;

import com.cibertec.t2.model.Alquiler;
import com.cibertec.t2.model.Cliente;
import com.cibertec.t2.model.Pelicula;
import com.cibertec.t2.service.AlquilerService;
import com.cibertec.t2.service.ClienteService;
import com.cibertec.t2.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {
    
    @Autowired
    private AlquilerService alquilerService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private PeliculaService peliculaService;
    
    @GetMapping
    public String listarAlquileres(Model model) {
        List<Alquiler> alquileres = alquilerService.findAll();
        List<Cliente> clientes = clienteService.findAll();
        List<Pelicula> peliculas = peliculaService.findAll();
        
        model.addAttribute("alquileres", alquileres);
        model.addAttribute("clientes", clientes);
        model.addAttribute("peliculas", peliculas);
        
        return "alquileres";
    }
    
    @PostMapping("/guardar")
    public String guardarAlquiler(
            @RequestParam Long idCliente,
            @RequestParam String estado,
            @RequestParam(value = "idPelicula[]", required = false) Long[] idPeliculas,
            @RequestParam(value = "cantidad[]", required = false) Integer[] cantidades,
            RedirectAttributes redirectAttributes) {
        
        try {
            if (idPeliculas != null && cantidades != null) {
                List<Long> peliculaIds = Arrays.asList(idPeliculas);
                List<Integer> cantidadesList = Arrays.asList(cantidades);
                
                alquilerService.createAlquiler(idCliente, estado, peliculaIds, cantidadesList);
                redirectAttributes.addFlashAttribute("success", "Alquiler guardado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un detalle de alquiler");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar alquiler: " + e.getMessage());
        }
        
        return "redirect:/alquileres";
    }
    
    @PostMapping("/actualizar")
    public String actualizarAlquiler(
            @RequestParam Long id,
            @RequestParam Long idCliente,
            @RequestParam String estado,
            @RequestParam(value = "idPelicula[]", required = false) Long[] idPeliculas,
            @RequestParam(value = "cantidad[]", required = false) Integer[] cantidades,
            RedirectAttributes redirectAttributes) {
        
        try {
            Optional<Alquiler> alquilerOpt = alquilerService.findById(id);
            Optional<Cliente> clienteOpt = clienteService.findById(idCliente);
            
            if (alquilerOpt.isPresent() && clienteOpt.isPresent()) {
                Alquiler alquiler = alquilerOpt.get();
                alquiler.setCliente(clienteOpt.get());
                alquiler.setEstado(estado);
                
                // Clear existing detalles and add new ones
                alquiler.getDetalles().clear();
                
                if (idPeliculas != null && cantidades != null) {
                    double total = 0.0;
                    for (int i = 0; i < idPeliculas.length; i++) {
                        Optional<Pelicula> peliculaOpt = peliculaService.findById(idPeliculas[i]);
                        if (peliculaOpt.isPresent()) {
                            com.cibertec.t2.model.DetalleAlquiler detalle = new com.cibertec.t2.model.DetalleAlquiler();
                            detalle.setPelicula(peliculaOpt.get());
                            detalle.setCantidad(cantidades[i]);
                            detalle.setAlquiler(alquiler);
                            alquiler.getDetalles().add(detalle);
                            total += cantidades[i];
                        }
                    }
                    alquiler.setTotal(total);
                }
                
                alquilerService.save(alquiler);
                redirectAttributes.addFlashAttribute("success", "Alquiler actualizado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Alquiler o cliente no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar alquiler: " + e.getMessage());
        }
        
        return "redirect:/alquileres";
    }
    
    @PostMapping("/eliminar")
    public String eliminarAlquiler(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            alquilerService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Alquiler eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar alquiler: " + e.getMessage());
        }
        return "redirect:/alquileres";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Alquiler> obtenerAlquiler(@PathVariable Long id) {
        return alquilerService.findById(id);
    }
} 