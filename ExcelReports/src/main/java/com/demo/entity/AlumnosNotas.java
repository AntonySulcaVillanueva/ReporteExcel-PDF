package com.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "alumnos")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlumnosNotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="salon")
    private String salon;

    @Column(name="numero_aula")
    private String numeroAula;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name="nota1")
    private Integer nota1;

    @Column(name = "nota2")
    private Integer nota2;

    @Column(name = "nota3")
    private Integer nota3;

    @Column(name = "nota4")
    private Integer nota4;

    @Column(name = "nota_final")
    private Double notafinal;



}
