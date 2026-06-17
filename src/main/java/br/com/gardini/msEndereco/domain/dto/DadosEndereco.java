package br.com.gardini.msEndereco.domain.dto;

import br.com.gardini.msEndereco.domain.entity.Endereco;

public record DadosEndereco(
        String nome,
        String cep,
        String numero,
        String complemento,
        String logradouro,
        String bairro,
        String uf
) {

    public DadosEndereco(Endereco endereco) {
        this(endereco.getNome(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getUf()
        );
    }

}
