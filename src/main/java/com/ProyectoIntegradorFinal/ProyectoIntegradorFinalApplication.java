package com.ProyectoIntegradorFinal;

import com.ProyectoIntegradorFinal.entity.ERole;
import com.ProyectoIntegradorFinal.entity.RolUsuario;
import com.ProyectoIntegradorFinal.entity.Usuario;
import com.ProyectoIntegradorFinal.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@ComponentScan("com.ProyectoIntegradorFinal")
public class ProyectoIntegradorFinalApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProyectoIntegradorFinalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoIntegradorFinalApplication.class, args);
		LOGGER.info("Proyecto is now running ...");
	}


	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UsuarioRepository userRepository;
	/*@Bean
	CommandLineRunner init(){
		return args -> {

			Set<RolUsuario> roles = new HashSet<>();
			RolUsuario adminRole = new RolUsuario();
			adminRole.setName(ERole.ADMIN);
			roles.add(adminRole);

			Usuario userEntity = new Usuario();
			userEntity.setEmail("procurac_spenal@juscatamarca.gob.ar");
			userEntity.setUsername("secretariapenal");
			userEntity.setPassword(passwordEncoder.encode("Secretaria_1200"));
			userEntity.setRoles(roles);


			userRepository.save(userEntity);
		};
	}*/
}
