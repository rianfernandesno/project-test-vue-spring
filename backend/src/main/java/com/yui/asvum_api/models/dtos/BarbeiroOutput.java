package com.yui.asvum_api.models.dtos;

import java.util.List;
import java.util.Set;

import com.yui.asvum_api.models.enums.Especialidade;

public record BarbeiroOutput(
        Long id,
        String nome,
        String fotoPath,
        Set<Especialidade> especialidades,
        List<HorarioTrabalhoOutput> horariosTrabalho, // Lista de DTOs, não da Entity!
        Boolean estaAtivo
) {}
