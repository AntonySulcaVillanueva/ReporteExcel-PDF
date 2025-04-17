package com.demo.service.impl;

import com.demo.entity.AlumnosNotas;
import com.demo.repository.AlumnosNotasRepository;
import com.demo.service.AlumnosService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnosServiceImpl implements AlumnosService {

    private final AlumnosNotasRepository alumnosNotasRepository;

    public AlumnosServiceImpl(AlumnosNotasRepository repository) {
        this.alumnosNotasRepository = repository;
    }

    @Override
    public List<AlumnosNotas> listarAlumnosNotas() {
        return alumnosNotasRepository.findAll();
    }

    @Override
    public AlumnosNotas guardar(AlumnosNotas alumnosNotas) {
        return alumnosNotasRepository.save(alumnosNotas);
    }

    @Override
    public Optional<AlumnosNotas> mostrarAlumnoPorId(Integer id) {
        return alumnosNotasRepository.findById(id);
    }

    @Override
    public AlumnosNotas editarAlumnosNota(AlumnosNotas alumnosNotas) {
        if (alumnosNotas.getId() != null) {
            return alumnosNotasRepository.save(alumnosNotas);
        } else {
            throw new IllegalArgumentException("El id del alumnos no puede ser nulo");
        }
    }

    @Override
    public void eliminarAlumnosNota(Integer id) {
        alumnosNotasRepository.deleteById(id);

    }


}
