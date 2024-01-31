package com.actividad.peliculas.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.actividad.peliculas.model.pojo.PeliculaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.actividad.peliculas.model.pojo.Pelicula;
import com.actividad.peliculas.model.request.CreatePeliculaRequest;
import com.actividad.peliculas.service.PeliculasService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Peliculas Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre peliculas alojados en una base de datos en memoria.")
public class PeliculasController {

    private final PeliculasService service;

    @GetMapping("/peliculas")
    @Operation(
            operationId = "Obtener peliculas",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todas las peliculas almacenadas en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pelicula.class)))
    public ResponseEntity<List<Pelicula>> getPeliculas(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "titulo", description = "Titulo de la pelicula. No tiene por que ser exacto", example = "Proyecto X", required = false)
            @RequestParam(required = false) String titulo,
            @Parameter(name = "director", description = "Nombre del director de la pelicula. No tiene por que ser exacto", example = "Nima Nourizadeh", required = false)
            @RequestParam(required = false) String director,
            @Parameter(name = "anio", description = "Año que se estreno la pelicula. Tiene que ser exacto", example = "2012", required = false)
            @RequestParam(required = false) String anio,
            @Parameter(name = "criticas", description = "Criticas de la pelicula.  No tiene que ser exacto", example = "Excelente pelicula", required = false)
            @RequestParam(required = false) String criticas,
            @Parameter(name = "sinopsis", description = "Sinopsis de la pelicula.  No tiene que ser exacto", example = "La pelicula empieza con una trama de suspenso.", required = false)
            @RequestParam(required = false) String sinopsis,
            @Parameter(name = "duracion", description = "Duración de la pelicula.  No tiene que ser exacto", example = "1h 32m", required = false)
            @RequestParam(required = false) String duracion,
            @Parameter(name = "trailer", description = "URL del trailer de la pelicula.  No tiene que ser exacto", example = "https://youtu.be/3CKvT_Z1SIs", required = false)
            @RequestParam(required = false) String trailer,
            @Parameter(name = "precioAlquiler", description = "Precio por el alquiler de la pelicula. Tiene que ser exacto", example = "6.99", required = false)
            @RequestParam(required = false) String precioAlquiler,
            @Parameter(name = "precioCompra", description = "Precio por la compra de la pelicula. Tiene que ser exacto", example = "10.99", required = false)
            @RequestParam(required = false) String precioCompra,
            @Parameter(name = "disponibilidad", description = "Disponibilidad de la pelicula, está o no rentada o en stock. Tiene que ser exacta", example = "true", required = false)
            @RequestParam(required = false) Boolean disponibilidad) {

        log.info("headers: {}", headers);
        List<Pelicula> peliculas = service.getPeliculas(titulo, director, anio, criticas, sinopsis, duracion, trailer, precioAlquiler, precioCompra, disponibilidad);

        if (peliculas != null) {
            return ResponseEntity.ok(peliculas);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/peliculas/{peliculaId}")
    @Operation(
            operationId = "Obtener un pelicula",
            description = "Operacion de lectura",
            summary = "Se devuelve una pelicula a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pelicula.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado la pelicula con el identificador indicado.")
    public ResponseEntity<Pelicula> getPelicula(@PathVariable String peliculaId) {

        log.info("Request received for pelicula {}", peliculaId);
        Pelicula pelicula = service.getPelicula(peliculaId);

        if (pelicula != null) {
            return ResponseEntity.ok(pelicula);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/peliculas")
    @Operation(
            operationId = "Insertar una pelicula",
            description = "Operacion de escritura",
            summary = "Se crea una pelicula a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la pelicula a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatePeliculaRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pelicula.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado la pelicula con el identificador indicado.")
    public ResponseEntity<Pelicula> addPelicula(@RequestBody CreatePeliculaRequest request) {

        Pelicula createdPelicula = service.createPelicula(request);

        if (createdPelicula != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPelicula);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/peliculas/{peliculaId}")
    @Operation(
            operationId = "Modificar parcialmente una pelicula",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente una pelicula.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la pelicula a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pelicula.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Pelicula inválida o datos incorrectos introducidos.")
    public ResponseEntity<Pelicula> patchPelicula(@PathVariable String peliculaId, @RequestBody String patchBody) {

        Pelicula patched = service.updatePelicula(peliculaId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/peliculas/{peliculaId}")
    @Operation(
            operationId = "Modificar totalmente una pelicula.",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente una pelicula.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la pelicula a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = PeliculaDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pelicula.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Pelicula no encontrada.")
    public ResponseEntity<Pelicula> updatePelicula(@PathVariable String peliculaId, @RequestBody PeliculaDto body) {

        Pelicula updated = service.updatePelicula(peliculaId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
