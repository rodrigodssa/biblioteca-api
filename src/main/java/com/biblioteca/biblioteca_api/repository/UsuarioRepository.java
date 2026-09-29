package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
