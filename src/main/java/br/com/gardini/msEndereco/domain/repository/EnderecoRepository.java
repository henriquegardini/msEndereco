package br.com.gardini.msEndereco.domain.repository;

import br.com.gardini.msEndereco.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Boolean existsByNomeIgnoringCaseAndCepIgnoringCaseAndNumeroIgnoringCaseAndComplementoIgnoringCase(String nome, String cep, String numero, String complemento);

    Optional<List<Endereco>> findByNomeIgnoreCase(String nome);

}
