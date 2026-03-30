package com.javier.habituall.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.javier.habituall.entity.Tarea;
import com.javier.habituall.entity.Usuario;

public interface TareaRepository extends JpaRepository<Tarea, Long>{
    List<Tarea> findByUsuario(Usuario usuario);
}
