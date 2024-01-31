package com.actividad.peliculas.model.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "peliculas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", unique = true)
    private String titulo;

    @Column(name = "director")
    private String director;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "criticas")
    private String criticas;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "precioAlquiler")
    private Double precioAlquiler;

    @Column(name = "precioCompra")
    private Double precioCompra;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    public void update(PeliculaDto peliculaDto) {

        this.titulo = peliculaDto.getTitulo();
        this.director = peliculaDto.getDirector();
        this.anio = Integer.parseInt(peliculaDto.getAnio());
        this.criticas = peliculaDto.getCriticas();
        this.sinopsis = peliculaDto.getSinopsis();
        this.duracion = peliculaDto.getDuracion();
        this.trailer = peliculaDto.getTrailer();
        this.precioAlquiler = Double.parseDouble(peliculaDto.getPrecioAlquiler());
        this.precioCompra = Double.parseDouble(peliculaDto.getPrecioCompra());
        this.disponibilidad = peliculaDto.getDisponibilidad();
    }

}
