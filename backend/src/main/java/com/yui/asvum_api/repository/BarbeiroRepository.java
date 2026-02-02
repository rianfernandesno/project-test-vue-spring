package com.yui.asvum_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yui.asvum_api.models.entities.Barbeiro;

public interface BarbeiroRepository  extends JpaRepository<Barbeiro, Long>{

     @Override
     @EntityGraph(attributePaths = {"especialidades", "horariosTrabalho"})
     List<Barbeiro> findAll();

     Optional<Barbeiro> findByNome(String name);
}
