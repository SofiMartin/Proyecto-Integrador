package com.ProyectoIntegradorFinal.security;

import com.ProyectoIntegradorFinal.repository.UsuarioRepository;
import com.ProyectoIntegradorFinal.security.filters.JwtAuthenticationFilter;
import com.ProyectoIntegradorFinal.security.filters.JwtAuthorizationFilter;
import com.ProyectoIntegradorFinal.security.jwt.JwtUtils;
import com.ProyectoIntegradorFinal.service.imp.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;


@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    UsuarioService userDetailsService;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    @Bean
    CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieName("XSRF-TOKEN"); // Nombre de la cookie
        csrfTokenRepository.setHeaderName("X-CSRF-TOKEN"); // Nombre del encabezado para el token CSRF
        return csrfTokenRepository;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils,userRepository);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");



        return httpSecurity
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:5174","http://reactjs-proyecto-integrador-frontende6.s3-website-us-east-1.amazonaws.com", "http://localhost:3000", "http://127.0.0.1:5173", "https://www.facebook.com"));
                            //config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Agrega aquí los orígenes permitidos
                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Agrega aquí los métodos permitidos
                            config.setAllowedHeaders(Arrays.asList("*")); // Agrega aquí los encabezados permitidos
                            config.setExposedHeaders(Arrays.asList("X-CSRF-TOKEN")); // Exponer el encabezado del token CSRF
                            config.setAllowCredentials(true); // Permitir credenciales (cookies, autenticación)
                            return config;
                        })
                )
                /*.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
                        .ignoringRequestMatchers("/login"))*/ // Ignorar CSRF para la URL de inicio de sesión
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    //auth.requestMatchers("/motorhome").permitAll();
                    auth.requestMatchers("/categoria").permitAll();
                    auth.requestMatchers("/usuarios").permitAll();
                    auth.requestMatchers("/motorhome").permitAll();
                    auth.requestMatchers("/imagenes/buscarPorProducto").permitAll();
                    auth.requestMatchers("/usuarios/createUser").permitAll();
                    auth.requestMatchers("/usuarios/eliminar/{id}").permitAll();
                    auth.requestMatchers("/usuarios/rol/{id}").permitAll();
                    auth.requestMatchers("/usuarios/buscar/{nombre}").permitAll();
                    auth.requestMatchers("/motorhome/registrar").permitAll();
                    auth.requestMatchers("/motorhome/actualizar").permitAll();
                    auth.requestMatchers("/motorhome/eliminar/{id}").permitAll();
                    auth.requestMatchers("/motorhome/detalle/{nombre}").permitAll();
                    auth.requestMatchers("/motorhome/detalle/id/{id}").permitAll();
                    auth.requestMatchers("/motorhome/categoria/{categoryId}").permitAll();
                    auth.requestMatchers("/motorhome/categoria/all").permitAll();
                    auth.requestMatchers("/motorhome/caracteristica/{caracteristicaId}").permitAll();
                    auth.requestMatchers("/imagenes/guardar").permitAll();
                    auth.requestMatchers("/email/sendMessage").permitAll();
                    auth.requestMatchers("/categoria/registrar").permitAll();
                    auth.requestMatchers("/categoria/eliminar/{id}").permitAll();
                    auth.requestMatchers("/categoria/detalle/{id}").permitAll();
                    auth.requestMatchers("/categoria/actualizar").permitAll();
                    auth.requestMatchers("/categoria/filtrar").permitAll();
                    auth.requestMatchers("/caracteristica").permitAll();
                    auth.requestMatchers("/caracteristica/registrar").permitAll();
                    auth.requestMatchers("/caracteristica/eliminar/{id}").permitAll();
                    auth.requestMatchers("/caracteristica/detalle/{id}").permitAll();
                    //auth.requestMatchers("/caracteristica/actualizar/{id}").permitAll();
                    auth.requestMatchers("/caracteristica/actualizar").permitAll();
                    auth.requestMatchers("/politica").permitAll();
                    auth.requestMatchers("/politica/registrar").permitAll();
                    auth.requestMatchers("/politica/eliminar/{id}").permitAll();
                    auth.requestMatchers("/politica/detalle/{id}").permitAll();
                    auth.requestMatchers("/politica/actualizar").permitAll();
                    auth.requestMatchers("/reserva").permitAll();
                    auth.requestMatchers("/motorhome/categoria/filtrar").permitAll();
                    auth.requestMatchers("/reserva/registrar").permitAll();
                    auth.requestMatchers("/reserva/buscarPorCategoriaYFechas").permitAll();
                    auth.requestMatchers("/reserva/reservaPorProducto/{productoId}").permitAll();
                    auth.requestMatchers("/reserva/fechasOcupadasPorProducto").permitAll();
                    //auth.requestMatchers("/internos/eliminar/{id}").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}
