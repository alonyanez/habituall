package com.javier.habituall.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TareaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaLimite;
    private boolean completado;
}
