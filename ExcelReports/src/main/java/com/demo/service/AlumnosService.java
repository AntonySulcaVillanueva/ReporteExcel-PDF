package com.demo.service;

import com.demo.entity.AlumnosNotas;

import java.util.List;
import java.util.Optional;

public interface AlumnosService {

    AlumnosNotas guardar(AlumnosNotas alumnosNotas);

    List<AlumnosNotas> listarAlumnosNotas();

    Optional<AlumnosNotas> mostrarAlumnoPorId(Integer id);

    AlumnosNotas editarAlumnosNota(AlumnosNotas alumnosNotas);

    void eliminarAlumnosNota(Integer id);


}
