package com.yui.asvum_api.models.dtos.input;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

import com.yui.asvum_api.models.enums.Especialidade;

public record ServicoInput(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
        String nome,

        @Size(max = 255, message = "A descrição é muito longa")
        String descricao,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A duração é obrigatória")
        @Min(value = 5, message = "A duração mínima é de 5 minutos")
        Integer duracao,

        @NotNull(message = "A especialidade é obrigatória")
        Especialidade especialidade
) {
}
