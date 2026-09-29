package com.biblioteca.biblioteca_api.service;

import com.biblioteca.biblioteca_api.exception.LivroIndisponivelException;
import com.biblioteca.biblioteca_api.model.Emprestimo;
import com.biblioteca.biblioteca_api.model.Livro;
import com.biblioteca.biblioteca_api.model.Usuario;
import com.biblioteca.biblioteca_api.repository.EmprestimoRepository;
import com.biblioteca.biblioteca_api.repository.LivroRepository;
import com.biblioteca.biblioteca_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
    void deveLancarExcecaoQuandoLivroIndisponivel() {
        // Implementar teste para verificar se a exceção é lançada quando o livro está indisponível
        Long livroId = 1L;
        Long usuarioId = 1L;

        // Configurar o mock para simular que o livro está indisponível
        // Chamar o metodo criar e verificar se a exceção é lançada

        when(emprestimoRepository.existsByLivroIdAndDataDevolucaoIsNull(livroId)).thenReturn(true);

        assertThrows(LivroIndisponivelException.class, () -> { emprestimoService.criar(livroId, usuarioId);
        });
    }

        @Test
        void deveCriarEmprestimoQuandoLivroDisponivel() {
            Long livroId = 1L;
            Long usuarioId = 1L;

            Livro livro = new Livro();
            livro.setId(livroId);
            livro.setTitulo("Dom Casmurro");

            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            usuario.setNome("Maria Silva");

            when(emprestimoRepository.existsByLivroIdAndDataDevolucaoIsNull(livroId))
                    .thenReturn(false);
            when(livroRepository.findById(livroId))
                    .thenReturn(Optional.of(livro));
            when(usuarioRepository.findById(usuarioId))
                    .thenReturn(Optional.of(usuario));
            when(emprestimoRepository.save(any(Emprestimo.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Emprestimo resultado = emprestimoService.criar(livroId, usuarioId);

            assertNotNull(resultado);
            assertEquals(livro, resultado.getLivro());
            assertEquals(usuario, resultado.getUsuario());
            assertNull(resultado.getDataDevolucao());
        }

}
