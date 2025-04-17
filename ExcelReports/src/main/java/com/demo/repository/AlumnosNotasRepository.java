package com.demo.repository;

import com.demo.entity.AlumnosNotas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnosNotasRepository extends JpaRepository<AlumnosNotas, Integer> {
}
