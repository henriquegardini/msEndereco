package br.com.gardini.msEndereco.infra.adapter;

import br.com.gardini.msEndereco.domain.RegraDeNegocioException;
import br.com.gardini.msEndereco.domain.dto.DadosBuscaCep;
import br.com.gardini.msEndereco.domain.entity.Log;
import br.com.gardini.msEndereco.domain.service.LogService;
import br.com.gardini.msEndereco.infra.port.BuscaEnderecoPeloCep;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Component
public class BuscaEnderecoPeloCepImpl implements BuscaEnderecoPeloCep {

    private final RestClient restClient;
    private final LogService logService;

    public BuscaEnderecoPeloCepImpl(RestClient.Builder restClientBuilder, LogService logService) {
        this.restClient = restClientBuilder.build();
        this.logService = logService;
    }

    @Override
    public DadosBuscaCep execute(String cep) {
        try {
            var headers = new HttpHeaders();
            var resposta = restClient.get()
                    .uri(String.format("http://viacep.com.br/ws/%s/json/", cep))
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(DadosBuscaCep.class);
            var log = new Log(LocalDateTime.now(), cep, resposta.toString());
            logService.salvarLog(log);
            return resposta;
        } catch (Exception e) {
            var log = new Log(LocalDateTime.now(), cep, e.getMessage().toString().substring(0, 255));
            logService.salvarLog(log);
            throw new RegraDeNegocioException("Ocorreu um erro na requisição do api externa da viacep!");
        }
    }

}
