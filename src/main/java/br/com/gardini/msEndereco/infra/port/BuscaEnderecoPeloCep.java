package br.com.gardini.msEndereco.infra.port;

import br.com.gardini.msEndereco.domain.dto.DadosBuscaCep;

public interface BuscaEnderecoPeloCep {
    DadosBuscaCep execute(String cep);
}
