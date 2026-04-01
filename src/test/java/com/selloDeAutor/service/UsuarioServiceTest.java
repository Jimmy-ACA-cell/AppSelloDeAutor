package com.selloDeAutor.service;

import com.selloDeAutor.model.Usuario;
import com.selloDeAutor.repository.RolRepository;
import com.selloDeAutor.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerNewUserAccount_withExistingEmail_shouldThrowException() {
        Usuario usuarioDTO = new Usuario();
        usuarioDTO.setEmail("test@test.com");
        
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.registerNewUserAccount(usuarioDTO);
        });

        assertEquals("Usuario Ya existe", exception.getMessage());
    }
}
