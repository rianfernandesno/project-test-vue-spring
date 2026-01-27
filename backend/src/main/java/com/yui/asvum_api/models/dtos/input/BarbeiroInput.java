package com.yui.asvum_api.models.dtos.input;

import java.util.List;
import java.util.Set;

import com.yui.asvum_api.models.enums.Especialidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record BarbeiroInput(
        @NotBlank String nome,
        String fotoPath,
        @NotEmpty Set<Especialidade> especialidades,
        List<HorarioTrabalhoInput> horariosTrabalho,
        Boolean estaAtivo
) {}
