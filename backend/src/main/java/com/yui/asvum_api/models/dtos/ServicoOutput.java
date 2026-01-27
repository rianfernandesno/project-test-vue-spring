package com.yui.asvum_api.models.dtos;

import java.math.BigDecimal;

import com.yui.asvum_api.models.enums.Especialidade;

public record ServicoOutput(
        Long id, 
        String nome,
        String descricao,
        BigDecimal preco,
        Integer duracao,
        Especialidade especialidade
) {
}
