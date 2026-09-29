package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    boolean existsByLivroIdAndDataDevolucaoIsNull(Long livroId);
}
