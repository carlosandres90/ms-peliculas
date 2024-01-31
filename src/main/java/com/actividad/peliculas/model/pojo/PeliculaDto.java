package com.actividad.peliculas.model.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PeliculaDto {
    private String titulo;
    private String director;
    private String anio;
    private String criticas;
    private String sinopsis;
    private String duracion;
    private String trailer;
    private String precioAlquiler;
    private String precioCompra;
    private Boolean disponibilidad;

}
