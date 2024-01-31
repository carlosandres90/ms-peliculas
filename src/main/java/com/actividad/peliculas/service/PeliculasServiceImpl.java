package com.actividad.peliculas.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.actividad.peliculas.data.PeliculaRepository;
import com.actividad.peliculas.model.pojo.PeliculaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.actividad.peliculas.model.pojo.Pelicula;
import com.actividad.peliculas.model.request.CreatePeliculaRequest;

@Service
@Slf4j
public class PeliculasServiceImpl implements PeliculasService {

    @Autowired
    private PeliculaRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private int anioInt;
    private double precioAlquiler1, precioCompra1;

    @Override
    public List<Pelicula> getPeliculas(String titulo, String director, String anio, String criticas, String sinopsis, String duracion, String trailer, String precioAlquiler, String precioCompra, Boolean disponibilidad){
        if (StringUtils.hasLength(titulo) || StringUtils.hasLength(director) || StringUtils.hasLength(anio)
                || StringUtils.hasLength(criticas) ||  StringUtils.hasLength(sinopsis) || StringUtils.hasLength(duracion)
                || StringUtils.hasLength(trailer) || StringUtils.hasLength(precioAlquiler) || StringUtils.hasLength(precioCompra) || disponibilidad != null) {

            if(StringUtils.hasLength(anio)){
                anioInt = Integer.parseInt(anio);
            }
            else {
                anioInt = -1;
            }

            if(StringUtils.hasLength(precioAlquiler)){
                precioAlquiler1 = Double.parseDouble(precioAlquiler);
            }
            else {
                precioAlquiler1 = -1;
            }

            if(StringUtils.hasLength(precioCompra)){
                precioCompra1 = Double.parseDouble(precioCompra);
            }
            else {
                precioCompra1 = -1;
            }


            return repository.search(titulo, director, anioInt, criticas, sinopsis, duracion, trailer, precioAlquiler1, precioCompra1, disponibilidad);
        }

        List<Pelicula> peliculas = repository.getPeliculas();
        return peliculas.isEmpty() ? null : peliculas;

    }

    @Override
    public Pelicula getPelicula(String peliculaId) {

        return repository.getById(Long.valueOf(peliculaId));
    }

    @Override
    public Pelicula createPelicula(CreatePeliculaRequest request) {

        //Otra opcion: Jakarta Validation: https://www.baeldung.com/java-validation
        if (request != null && StringUtils.hasLength(request.getTitulo().trim())
                && StringUtils.hasLength(request.getDirector().trim())
                && StringUtils.hasLength(request.getAnio().trim()) && StringUtils.hasLength(request.getCriticas().trim())
                && StringUtils.hasLength(request.getSinopsis().trim()) && StringUtils.hasLength(request.getDuracion().trim())
                && StringUtils.hasLength(request.getTrailer().trim()) && StringUtils.hasLength(request.getPrecioAlquiler().trim())
                && StringUtils.hasLength(request.getPrecioCompra().trim())
                && request.getDisponibilidad() != null) {

            anioInt = Integer.parseInt(request.getAnio());
            precioAlquiler1 = Double.parseDouble(request.getPrecioAlquiler());
            precioCompra1 = Double.parseDouble(request.getPrecioCompra());

            Pelicula pelicula = Pelicula.builder().titulo(request.getTitulo()).director(request.getDirector()).anio(anioInt)
                    .criticas(request.getCriticas()).sinopsis(request.getSinopsis()).duracion(request.getDuracion()).trailer(request.getTrailer())
                    .precioAlquiler(precioAlquiler1).precioCompra(precioCompra1)
                    .disponibilidad(request.getDisponibilidad()).build();

            return repository.save(pelicula);
        } else {
            return null;
        }
    }

    @Override
    public Pelicula updatePelicula(String peliculaId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        Pelicula pelicula = repository.getById(Long.valueOf(peliculaId));
        if (pelicula != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(pelicula)));
                Pelicula patched = objectMapper.treeToValue(target, Pelicula.class);
                repository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating pelicula {}", peliculaId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Pelicula updatePelicula(String peliculaId, PeliculaDto updateRequest) {
        Pelicula pelicula = repository.getById(Long.valueOf(peliculaId));
        if (pelicula != null) {
            pelicula.update(updateRequest);
            repository.save(pelicula);
            return pelicula;
        } else {
            return null;
        }
    }

}
