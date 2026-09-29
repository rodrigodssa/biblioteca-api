package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
