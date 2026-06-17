package br.com.gardini.msEndereco.domain.entity;

import br.com.gardini.msEndereco.domain.dto.DadosBuscaCep;
import br.com.gardini.msEndereco.domain.dto.DadosCadastroEndereco;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String uf;

    public Endereco() {
    }

    public Endereco(DadosCadastroEndereco dadosCadastro, DadosBuscaCep dadosBuscaCep) {
        this.nome = dadosCadastro.nome();
        this.cep = dadosCadastro.cep();
        this.logradouro = dadosBuscaCep.logradouro();
        this.numero = dadosCadastro.numero();
        this.complemento = dadosCadastro.complemento();
        this.bairro = dadosBuscaCep.bairro();
        this.uf = dadosBuscaCep.uf();
    }
}
