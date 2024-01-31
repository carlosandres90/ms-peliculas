package com.actividad.peliculas.data;

import com.actividad.peliculas.data.utils.SearchCriteria;
import com.actividad.peliculas.data.utils.SearchOperation;
import com.actividad.peliculas.data.utils.SearchStatement;
import com.actividad.peliculas.model.pojo.Pelicula;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PeliculaRepository {

    private final PeliculaJpaRepository repository;

    public List<Pelicula> getPeliculas() {
        return repository.findAll();
    }

    public Pelicula getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Pelicula save(Pelicula pelicula) {
        return repository.save(pelicula);
    }

    public List<Pelicula> search(String titulo, String director, Integer anio, String criticas, String sinopsis, String duracion, String trailer, Double precioAlquiler, Double precioCompra, Boolean disponibilidad) {
        SearchCriteria<Pelicula> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(titulo)) {
            spec.add(new SearchStatement("titulo", titulo, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(director)) {
            spec.add(new SearchStatement("director", director, SearchOperation.MATCH));
        }

        if (anio >= 0) {
            spec.add(new SearchStatement("anio", anio, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(criticas)) {
            spec.add(new SearchStatement("criticas", criticas, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(sinopsis)) {
            spec.add(new SearchStatement("sinopsis", sinopsis, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(duracion)) {
            spec.add(new SearchStatement("duracion", duracion, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(trailer)) {
            spec.add(new SearchStatement("trailer", trailer, SearchOperation.MATCH));
        }

        if (precioAlquiler >= 0) {
            spec.add(new SearchStatement("precioAlquiler", precioAlquiler, SearchOperation.EQUAL));
        }

        if (precioCompra >= 0) {
            spec.add(new SearchStatement("precioCompra", precioCompra, SearchOperation.EQUAL));
        }

        if (disponibilidad != null) {
            spec.add(new SearchStatement("disponibilidad", disponibilidad, SearchOperation.EQUAL));
        }

        return repository.findAll(spec);
    }
}
