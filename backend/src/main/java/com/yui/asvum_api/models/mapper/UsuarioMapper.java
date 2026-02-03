package com.yui.asvum_api.models.mapper;

import com.yui.asvum_api.models.dtos.UsuarioOutput;
import com.yui.asvum_api.models.dtos.input.UsuarioInput;
import com.yui.asvum_api.models.entities.Role;
import com.yui.asvum_api.models.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // 1. Entidade -> Output (Transforma a lista de objetos Role em lista de Strings)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStrings")
    UsuarioOutput toOutput(Usuario usuario);

    // 2. Input -> Entidade (Ignora as roles para você tratar manualmente no Service via ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true) 
    Usuario toEntity(UsuarioInput input);

    // Helper para converter os nomes das roles para o Output
    @Named("mapRolesToStrings")
    default List<String> mapRolesToStrings(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
