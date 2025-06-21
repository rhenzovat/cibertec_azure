package com.cibertec.t2.controller;

import com.cibertec.t2.model.Cliente;
import com.cibertec.t2.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }
    
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/actualizar")
    public String actualizarCliente(@RequestParam Long id, @ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            Cliente updated = clienteService.update(id, cliente);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Cliente actualizado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/eliminar")
    public String eliminarCliente(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado exitosamente");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el cliente porque tiene alquileres asociados");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.findById(id);
    }
} 