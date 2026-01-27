package com.yui.asvum_api.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.yui.asvum_api.models.dtos.ServicoOutput;
import com.yui.asvum_api.models.dtos.input.ServicoInput;
import com.yui.asvum_api.models.entities.Servico;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    ServicoOutput toOutput(Servico servico);

    @Mapping(target = "id", ignore = true)
    Servico toEntity(ServicoInput input);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ServicoInput input, @MappingTarget Servico entity);
}
