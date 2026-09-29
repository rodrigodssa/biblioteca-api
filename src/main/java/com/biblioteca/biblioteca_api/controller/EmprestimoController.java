package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.model.Emprestimo;
import com.biblioteca.biblioteca_api.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Emprestimo> criar(@RequestParam long livroId, @RequestParam long usuarioId) {
        Emprestimo emprestimo = emprestimoService.criar(livroId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Emprestimo> devolver(@PathVariable long id) {
        Emprestimo emprestimo = emprestimoService.devolver(id);
        return ResponseEntity.ok(emprestimo);
    }
}
