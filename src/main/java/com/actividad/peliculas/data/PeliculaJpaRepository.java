package com.actividad.peliculas.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.actividad.peliculas.model.pojo.Pelicula;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PeliculaJpaRepository extends JpaRepository<Pelicula, Long>, JpaSpecificationExecutor<Pelicula>{

    List<Pelicula> findByTitulo(String titulo);

    List<Pelicula> findByDirector(String director);

    List<Pelicula> findByAnio(Integer anio);

    List<Pelicula> findByCriticas(String criticas);

    List<Pelicula> findBySinopsis(String sinopsis);

    List<Pelicula> findByDuracion(String duracion);

    List<Pelicula> findByTrailer(String trailer);

    List<Pelicula> findByPrecioAlquiler(Double precioAlquiler);

    List<Pelicula> findByPrecioCompra(Double precioCompra);

    List<Pelicula> findByDisponibilidad(Boolean disponibilidad);


}
