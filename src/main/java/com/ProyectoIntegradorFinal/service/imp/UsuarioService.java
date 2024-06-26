package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.dto.UsuarioDto;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.entity.Usuario;
import com.ProyectoIntegradorFinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario userEntity = userRepository.getName(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
                .collect(Collectors.toSet());

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }



    public List<UsuarioDto> listarUsuario() {
        List<Usuario> usuarios = userRepository.findAll();
        List<UsuarioDto> usuarioDtos = usuarios.stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDto.class)).toList();
        LOGGER.info("Listado de todos los motorhomes: {}", usuarioDtos);
        return usuarioDtos;
    }
}
