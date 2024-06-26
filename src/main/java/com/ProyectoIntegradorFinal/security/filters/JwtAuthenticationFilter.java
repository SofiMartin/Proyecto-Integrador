package com.ProyectoIntegradorFinal.security.filters;

import com.ProyectoIntegradorFinal.entity.ERole;
import com.ProyectoIntegradorFinal.entity.RolUsuario;
import com.ProyectoIntegradorFinal.entity.Usuario;
import com.ProyectoIntegradorFinal.repository.UsuarioRepository;
import com.ProyectoIntegradorFinal.security.jwt.JwtUtils;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtUtils jwtUtils;
    private UsuarioRepository userRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UsuarioRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UsuarioRepository userRepository) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public JwtAuthenticationFilter(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        Usuario userEntity = null;
        String username = "";
        String password = "";
        try{
            userEntity = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = userEntity.getUsername();
            password = userEntity.getPassword();
            logger.info("Intentando autenticar usuario: {}");
        } catch (StreamReadException e) {
            logger.error("Error al leer el flujo de entrada", e);
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            logger.error("Error al hacer binding del objeto JSON", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Error de entrada/salida", e);
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        //String token = jwtUtils.generateAccesToken(user.getUsername());

        // Intenta obtener el Usuario directamente del contexto de seguridad
        Usuario user2 = null;
        Object principal = authResult.getPrincipal();
        if (principal instanceof User) {
            // Si el principal es un User, intenta obtener el Usuario de la base de datos
            String username = ((User) principal).getUsername();
            user2 = userRepository.findByUsername(username).orElse(null);; // Ajusta según tu lógica para obtener el Usuario
        }


        String email = user2.getEmail(); // Obtener el correo electrónico desde la entidad Usuario
        //String email = getEmailFromAuthentication(authResult);
        Set<ERole> roles = getRolesFromAuthentication(authResult);
        String token = jwtUtils.generateAccesToken(user.getUsername(), email, roles);

        response.addHeader("Authorization", token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("Message", "Autenticacion Correcta");
        httpResponse.put("Username", user.getUsername());

        httpResponse.put("Email", email);
        httpResponse.put("Roles", getRolesFromAuthentication(authResult));

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    private String getEmailFromAuthentication(Authentication auth) {
        if (auth.getPrincipal() instanceof User) {
            Usuario user = (Usuario) auth.getPrincipal();
            return user.getEmail(); // Usar el username como email (ajusta según tu lógica)
        }
        return null;
    }

    private Set<ERole> getRolesFromAuthentication(Authentication auth) {
        if (auth.getAuthorities() != null) {
            return auth.getAuthorities().stream()
                    .map(authority -> {
                        for (ERole role : ERole.values()) {
                            if (authority.getAuthority().equals("ROLE_" + role.name())) {
                                return role;
                            }
                        }
                        throw new IllegalArgumentException("No enum constant found for: " + authority.getAuthority());
                    })
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

}
