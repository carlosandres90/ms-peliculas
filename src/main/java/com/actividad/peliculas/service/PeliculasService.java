package com.actividad.peliculas.service;

import java.util.List;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.actividad.peliculas.model.pojo.Pelicula;
import com.actividad.peliculas.model.pojo.PeliculaDto;
import com.actividad.peliculas.model.request.CreatePeliculaRequest;


public interface PeliculasService {

    List<Pelicula> getPeliculas(String titulo, String director, String anio, String criticas, String sinopsis, String duracion, String trailer, String precioAlquiler, String precioCompra, Boolean disponibilidad);

    Pelicula getPelicula(String peliculaId);

    Pelicula createPelicula(CreatePeliculaRequest request);

    Pelicula updatePelicula(String peliculaId, String updateRequest);

    Pelicula updatePelicula(String peliculaId, PeliculaDto updateRequest);

}
