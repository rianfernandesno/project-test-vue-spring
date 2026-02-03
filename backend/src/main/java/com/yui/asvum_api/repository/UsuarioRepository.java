package com.yui.asvum_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yui.asvum_api.models.entities.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

}
