package com.yui.asvum_api.models.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yui.asvum_api.models.dtos.BarbeiroOutput;
import com.yui.asvum_api.models.dtos.HorarioTrabalhoOutput;
import com.yui.asvum_api.models.dtos.input.BarbeiroInput;
import com.yui.asvum_api.models.dtos.input.HorarioTrabalhoInput;
import com.yui.asvum_api.models.entities.Barbeiro;
import com.yui.asvum_api.models.entities.HorarioTrabalho;

@Mapper(componentModel = "spring")
public abstract class BarbeiroMapper {

    // 1. Mapeamento de Saída: Usamos uma expressão Java para montar a URL
    @Mapping(target = "fotoPath", expression = "java(buildFotoUrl(barbeiro))")
    public abstract BarbeiroOutput toOutput(Barbeiro barbeiro);
    
    public abstract HorarioTrabalhoOutput toOutput(HorarioTrabalho horario);

    // 2. Mapeamento de Entrada
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fotoPath", ignore = true) // O path é definido no Service via upload
    public abstract Barbeiro toEntity(BarbeiroInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "barbeiro", ignore = true)
    public abstract HorarioTrabalho toEntity(HorarioTrabalhoInput input);

    // 3. Atualização
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fotoPath", ignore = true) // Não sobrescrever o path via DTO simples
    public abstract void updateEntityFromDto(BarbeiroInput input, @MappingTarget Barbeiro entity);

    /**
     * Lógica para construir a URL dinâmica baseada no contexto da requisição.
     */
    protected String buildFotoUrl(Barbeiro barbeiro) {
        if (barbeiro.getFotoPath() == null || barbeiro.getFotoPath().isEmpty()) {
            return null;
        }

        // Pega automaticamente o protocolo (http/https), host e porta da requisição atual
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        // Retorna a URL completa: http://dominio/uploads/barbeiros/{id}/imagens/{nomeArquivo}
        return baseUrl + "/uploads/barbeiros/" + barbeiro.getId() + "/imagens/" + barbeiro.getFotoPath();
    }

    @AfterMapping
    protected void linkHorarios(@MappingTarget Barbeiro barbeiro) {
        if (barbeiro.getHorariosTrabalho() != null) {
            barbeiro.getHorariosTrabalho().forEach(horario -> horario.setBarbeiro(barbeiro));
        }
    }
}