package com.ProyectoIntegradorFinal.repository;

import com.ProyectoIntegradorFinal.entity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RolUsuario, Long> {
}
