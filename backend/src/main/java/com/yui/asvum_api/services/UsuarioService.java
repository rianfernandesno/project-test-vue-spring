package com.yui.asvum_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yui.asvum_api.models.dtos.UsuarioOutput;
import com.yui.asvum_api.models.dtos.input.UsuarioInput;
import com.yui.asvum_api.models.entities.Role;
import com.yui.asvum_api.models.entities.Usuario;
import com.yui.asvum_api.models.mapper.UsuarioMapper;
import com.yui.asvum_api.repository.RoleRepository;
import com.yui.asvum_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioMapper mapper;

    @Transactional(readOnly = true)
    public List<UsuarioOutput> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toOutput)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioOutput findById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return mapper.toOutput(usuario);
    }

    @Transactional
    public UsuarioOutput create(UsuarioInput input) {
        // Converte o DTO para Entidade (ignorando roles inicialmente)
        Usuario usuario = mapper.toEntity(input);
        
        // Associa as roles buscadas do banco
        usuario.setRoles(mapIdsToRoles(input.rolesIds()));
        
        usuario = repository.save(usuario);
        return mapper.toOutput(usuario);
    }

    @Transactional
    public UsuarioOutput update(Long id, UsuarioInput input) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza os dados básicos
        usuarioExistente.setFullName(input.fullName());
        usuarioExistente.setEmail(input.email());
        usuarioExistente.setPassword(input.password());
        
        // Atualiza a lista de roles
        usuarioExistente.setRoles(mapIdsToRoles(input.rolesIds()));

        usuarioExistente = repository.save(usuarioExistente);
        return mapper.toOutput(usuarioExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    // Método auxiliar para buscar Roles no banco de dados
    private List<Role> mapIdsToRoles(List<Long> rolesIds) {
        if (rolesIds == null || rolesIds.isEmpty()) {
            return List.of();
        }
        return roleRepository.findAllById(rolesIds);
    }
}