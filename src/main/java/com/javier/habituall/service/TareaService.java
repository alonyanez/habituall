package com.javier.habituall.service;

import java.util.List;

import com.javier.habituall.entity.Tarea;

public interface TareaService {
    Tarea crearTarea(Long usuarioId, Tarea tarea);
    List<Tarea> listarTareasPorUsuario(Long usuarioId);
    Tarea obtenerTarea(Long id);
    Tarea actualizarTarea(Tarea tarea);
    void eliminarTarea(Long id);
    Tarea marcarCompletada(Long id, boolean completada);
}
