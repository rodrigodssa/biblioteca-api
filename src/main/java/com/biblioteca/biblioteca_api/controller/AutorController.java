package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.model.Autor;
import com.biblioteca.biblioteca_api.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @GetMapping
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Autor> criar(@RequestBody Autor autor) {
        Autor autorCriado = autorRepository.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorCriado);
    }
}
