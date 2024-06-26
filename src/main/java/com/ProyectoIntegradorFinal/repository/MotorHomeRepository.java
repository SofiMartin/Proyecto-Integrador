package com.ProyectoIntegradorFinal.repository;

import com.ProyectoIntegradorFinal.entity.MotorHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorHomeRepository extends JpaRepository<MotorHome, Long> {

    //List<MotorHome> findByMotorHome(String modelo);
}
