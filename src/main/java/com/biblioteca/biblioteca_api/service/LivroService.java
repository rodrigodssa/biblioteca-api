package com.biblioteca.biblioteca_api.service;

import com.biblioteca.biblioteca_api.model.Autor;
import com.biblioteca.biblioteca_api.model.Livro;
import com.biblioteca.biblioteca_api.repository.AutorRepository;
import com.biblioteca.biblioteca_api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    public Livro criar(Livro livro) {
        Autor autorCompleto = autorRepository.findById(livro.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        livro.setAutor(autorCompleto);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livroExistente = buscarPorId(id);
        if (livroExistente == null) {
            return null;
        }
        livroExistente.setTitulo(livroAtualizado.getTitulo());
        if (livroAtualizado.getAutor() != null) {
            Autor autorCompleto = autorRepository.findById(livroAtualizado.getAutor().getId())
                    .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
            livroExistente.setAutor(autorCompleto);
        }
        return livroRepository.save(livroExistente);
    }

    public boolean deletar(Long id) {
        if (!livroRepository.existsById(id)) {
            return false;
        }
        livroRepository.deleteById(id);
        return true;
    }
}
