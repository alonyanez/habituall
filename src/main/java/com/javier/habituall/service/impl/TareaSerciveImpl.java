package com.javier.habituall.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javier.habituall.entity.Tarea;
import com.javier.habituall.entity.Usuario;
import com.javier.habituall.exception.ResourceNotFoundException;
import com.javier.habituall.repository.TareaRepository;
import com.javier.habituall.repository.UsuarioRepository;
import com.javier.habituall.service.TareaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TareaSerciveImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Tarea crearTarea(Long usuarioId, Tarea tarea) {
        tarea.setUsuario(usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id:" + usuarioId)));
        return tareaRepository.save(tarea); 
    }

    @Override
    public List<Tarea> listarTareasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario"));

        return tareaRepository.findByUsuario(usuario);
    }

    @Override
    public Tarea obtenerTarea(Long id) {
        return tareaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id:" + id));
    }

    @Override
    public Tarea actualizarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);

    }

    @Override
    public void eliminarTarea(Long id) {
       Tarea tarea = obtenerTarea(id);
       tareaRepository.delete(tarea);
    }

    @Override
    public Tarea marcarCompletada(Long id, boolean completada) {
        Tarea tarea = obtenerTarea(id);
        tarea.setCompletada(completada);
        return tareaRepository.save(tarea);
    }
    
}
