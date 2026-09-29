package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
