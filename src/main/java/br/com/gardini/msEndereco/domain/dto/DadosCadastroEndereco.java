package br.com.gardini.msEndereco.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroEndereco(
        @NotBlank(message = "Nome do evento é obrigatório!")
        String nome,
        @NotBlank(message = "O CEP é obrigatório!")
        String cep,
        @NotBlank(message = "O numero é obrigatório!")
        String numero,
        String complemento
) {
}
