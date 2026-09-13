package com.joao.RMAFlow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParceiroRequestDTO(
        @NotBlank String nome,
        @NotBlank String cpfCnpj,
        @NotBlank String endereco,
        String telefone,
        @Email String email
) {
}
