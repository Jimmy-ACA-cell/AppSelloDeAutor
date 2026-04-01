package com.selloDeAutor.service;

import com.selloDeAutor.model.Rol;
import com.selloDeAutor.model.Usuario;
import com.selloDeAutor.repository.RolRepository;
import com.selloDeAutor.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registerNewUserAccount(Usuario usuarioDTO) throws Exception{
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new Exception("Usuario Ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        Optional<Rol> rolUser = rolRepository.findByNombre("ROLE_CLIENT");
        Set<Rol> roles = new HashSet<>();
        if (rolUser.isPresent()) {
            roles.add(rolUser.get());
        }else {
            Rol nuevoRol = Rol.builder()
                    .nombre("ROLE_CLIENT")
                    .build();
            rolRepository.save(nuevoRol);
            roles.add(nuevoRol);
        }
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
         }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<GrantedAuthority> grantedAuthorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toSet());

        return new User(usuario.getEmail(), usuario.getPassword(), grantedAuthorities);

    }
}
