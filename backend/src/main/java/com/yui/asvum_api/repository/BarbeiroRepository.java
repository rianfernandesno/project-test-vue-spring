package com.yui.asvum_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yui.asvum_api.models.entities.Barbeiro;

public interface BarbeiroRepository  extends JpaRepository<Barbeiro, Long>{

     Optional<Barbeiro> findByNome(String name);
}
