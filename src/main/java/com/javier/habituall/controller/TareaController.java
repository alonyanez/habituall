package com.javier.habituall.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javier.habituall.entity.Tarea;
import com.javier.habituall.entity.Usuario;
import com.javier.habituall.repository.UsuarioRepository;
import com.javier.habituall.service.TareaService;

@RestController
@RequestMapping("/api/tarea")
public class TareaController {
    
    @Autowired
    private TareaService tareaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getUsuarioAutenticado() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                                            .getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));  
    }

    @PostMapping("/crearTarea")
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Usuario usuario = getUsuarioAutenticado();
        Tarea nueva = tareaService.crearTarea(usuario.getId(), tarea);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/listarTareas")
    public ResponseEntity<List<Tarea>> listarTareas() {
        Usuario usuario = getUsuarioAutenticado();
        return ResponseEntity.ok(tareaService.listarTareasPorUsuario(usuario.getId()));
    }

    @GetMapping("/obtenerTarea/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long id) {
        Usuario usuario = getUsuarioAutenticado();
        Tarea tarea = tareaService.obtenerTarea(id);

         //Se recomienda con equals pero como es Long se puede comparar con !=
        if(tarea.getUsuario().getId() != usuario.getId()) {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }
        return ResponseEntity.ok(tarea);
    }

    @PutMapping("/actualizarTarea/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        Usuario usuario = getUsuarioAutenticado();
        Tarea tareaExistente = tareaService.obtenerTarea(id);

        if(tareaExistente.getUsuario().getId() != usuario.getId()) {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }

        tareaExistente.setTitulo(tarea.getTitulo());
        tareaExistente.setDescripcion(tarea.getDescripcion());
        tareaExistente.setFechaLimite(tarea.getFechaLimite());

        Tarea actualizada = tareaService.actualizarTarea(tareaExistente);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminarTarea/{id}/completar")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id, @RequestParam boolean completada) {
        Usuario usuario = getUsuarioAutenticado();
        Tarea tareaExistente = tareaService.obtenerTarea(id);

        if(tareaExistente.getUsuario().getId() != usuario.getId()) {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }

        tareaService.eliminarTarea(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/marcarCompletada/{id}/completar")
    public ResponseEntity<Tarea> marcarCompletada(@PathVariable Long id, @RequestParam boolean completada) {
        Usuario usuario = getUsuarioAutenticado();
        Tarea tareaExistente = tareaService.obtenerTarea(id);

        if(tareaExistente.getUsuario().getId() != usuario.getId()) {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }

        Tarea actualizada = tareaService.marcarCompletada(id, completada);
        return ResponseEntity.ok(actualizada);
    }

}
