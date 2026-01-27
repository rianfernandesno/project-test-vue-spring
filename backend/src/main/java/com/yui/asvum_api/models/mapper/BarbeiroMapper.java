package com.yui.asvum_api.models.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.yui.asvum_api.models.dtos.BarbeiroOutput;
import com.yui.asvum_api.models.dtos.HorarioTrabalhoOutput;
import com.yui.asvum_api.models.dtos.input.BarbeiroInput;
import com.yui.asvum_api.models.dtos.input.HorarioTrabalhoInput;
import com.yui.asvum_api.models.entities.Barbeiro;
import com.yui.asvum_api.models.entities.HorarioTrabalho;

@Mapper(componentModel = "spring")
public interface BarbeiroMapper {

    // Entidade para DTO de Saída
    BarbeiroOutput toOutput(Barbeiro barbeiro);
    HorarioTrabalhoOutput toOutput(HorarioTrabalho horario);

    // DTO de Entrada para Entidade (Removido o ignore do horariosTrabalho)
    @Mapping(target = "id", ignore = true)
    Barbeiro toEntity(BarbeiroInput input);

    // Mapeamento necessário para converter os itens da lista
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "barbeiro", ignore = true)
    HorarioTrabalho toEntity(HorarioTrabalhoInput input);

    // Atualização de entidade existente
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(BarbeiroInput input, @MappingTarget Barbeiro entity);

    /**
     * Este método garante que cada HorarioTrabalho tenha a referência do Barbeiro
     * após o mapeamento, permitindo o salvamento em cascata do JPA.
     */
    @AfterMapping
    default void linkHorarios(@MappingTarget Barbeiro barbeiro) {
        if (barbeiro.getHorariosTrabalho() != null) {
            barbeiro.getHorariosTrabalho().forEach(horario -> horario.setBarbeiro(barbeiro));
        }
    }
}
