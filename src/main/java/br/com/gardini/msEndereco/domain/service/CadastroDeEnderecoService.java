package br.com.gardini.msEndereco.domain.service;

import br.com.gardini.msEndereco.domain.RegraDeNegocioException;
import br.com.gardini.msEndereco.domain.dto.DadosCadastroEndereco;
import br.com.gardini.msEndereco.domain.dto.DadosEndereco;
import br.com.gardini.msEndereco.domain.entity.Endereco;
import br.com.gardini.msEndereco.domain.mapper.EnderecoMapper;
import br.com.gardini.msEndereco.domain.repository.EnderecoRepository;
import br.com.gardini.msEndereco.infra.port.BuscaEnderecoPeloCep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroDeEnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final BuscaEnderecoPeloCep buscaEnderecoPeloCep;
    private final EnderecoMapper enderecoMapper;

    public CadastroDeEnderecoService(EnderecoRepository enderecoRepository, BuscaEnderecoPeloCep buscaEnderecoPeloCep, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.buscaEnderecoPeloCep = buscaEnderecoPeloCep;
        this.enderecoMapper = enderecoMapper;
    }

    public DadosEndereco cadastrarEndereco(DadosCadastroEndereco dadosCadastro) {
        var enderecoJaCadastrado = enderecoRepository.existsByNomeIgnoringCaseAndCepIgnoringCaseAndNumeroIgnoringCaseAndComplementoIgnoringCase(dadosCadastro.nome(), dadosCadastro.cep(), dadosCadastro.numero(), dadosCadastro.complemento());
        if (enderecoJaCadastrado) {
            throw new RegraDeNegocioException("Endereco já cadastrado com esse nome, cep, numero e complemento!");
        }

        var dadosBuscaCep = buscaEnderecoPeloCep.execute(dadosCadastro.cep());
        var endereco = new Endereco(dadosCadastro, dadosBuscaCep);
        enderecoRepository.save(endereco);
        return enderecoMapper.enderecoToDadosEndereco(endereco);
    }

    public List<DadosEndereco> listarEndereco(String nome) {
        var endereco = enderecoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado!"));
        return enderecoMapper.enderecoToDadosEnderecos(endereco);
    }

    public void deletarEnderecos(String nome) {
        var endereco = enderecoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado!"));
        endereco.forEach(e -> enderecoRepository.delete(e));
    }

}
