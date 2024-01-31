package com.actividad.peliculas.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePeliculaRequest {

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
