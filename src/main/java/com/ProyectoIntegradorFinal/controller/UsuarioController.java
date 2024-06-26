package com.ProyectoIntegradorFinal.controller;

import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.dto.UsuarioDto;
import com.ProyectoIntegradorFinal.entity.ERole;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.entity.RolUsuario;
import com.ProyectoIntegradorFinal.entity.Usuario;
import com.ProyectoIntegradorFinal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository userRepository;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UsuarioDto createUserDTO){

        Set<RolUsuario> roles = createUserDTO.getRoles().stream()
                .map(role -> new RolUsuario(ERole.valueOf(role)))
                .collect(Collectors.toSet());

        Usuario userEntity = new Usuario(
                createUserDTO.getEmail(),
                createUserDTO.getUsername(),
                passwordEncoder.encode(createUserDTO.getPassword()),
                roles
        );

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @GetMapping()
    public List<Usuario> listarTodos(){
        return userRepository.findAll();
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> buscarUsuario(@PathVariable String nombre){
        ResponseEntity<Optional<Usuario>> respuesta;
        Optional<Usuario> usuario = userRepository.findByUsername(nombre);
        if(usuario != null) respuesta = new ResponseEntity<>(usuario, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    @PutMapping("/rol/{id}")
    public ResponseEntity<?> cambiarRol(@PathVariable Long id){
        Usuario usuario = userRepository.findById(id).orElse(null);
        if (usuario != null) {
            Set<RolUsuario> rolesActuales = usuario.getRoles();



            if (tieneRol(rolesActuales, ERole.USER)) {
                // Si tiene el rol USER, quítalo y agrega el rol ADMIN
                rolesActuales.removeIf(rol -> rol.getName().equals(ERole.USER));
                rolesActuales.add(new RolUsuario(ERole.ADMIN));
            } else if (tieneRol(rolesActuales, ERole.ADMIN)) {
                // Si tiene el rol ADMIN, quítalo y agrega el rol USER
                rolesActuales.removeIf(rol -> rol.getName().equals(ERole.ADMIN));
                rolesActuales.add(new RolUsuario(ERole.USER));
            }

            // Guardar los cambios en la base de datos
            userRepository.save(usuario);

            return ResponseEntity.ok("Roles cambiados: " + usuario.getRoles());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método auxiliar para verificar si un usuario tiene un rol específico
    private boolean tieneRol(Set<RolUsuario> roles, ERole rolBuscado) {
        return roles.stream().anyMatch(rol -> rol.getName().equals(rolBuscado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUser(@PathVariable Long id){
        Usuario usuario = userRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado: "+usuario);
    }

}
