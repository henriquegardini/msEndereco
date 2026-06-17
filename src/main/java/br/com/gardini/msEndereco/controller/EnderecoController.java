package br.com.gardini.msEndereco.controller;

import br.com.gardini.msEndereco.domain.dto.DadosCadastroEndereco;
import br.com.gardini.msEndereco.domain.dto.DadosEndereco;
import br.com.gardini.msEndereco.domain.service.CadastroDeEnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("endereco")
public class EnderecoController {

    private final CadastroDeEnderecoService cadastroDeEnderecoService;

    public EnderecoController(CadastroDeEnderecoService cadastroDeEnderecoService) {
        this.cadastroDeEnderecoService = cadastroDeEnderecoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosEndereco> cadastrar(@RequestBody @Valid DadosCadastroEndereco dadosParaCadastro, UriComponentsBuilder uriComponentsBuilder) {
        var dadosEnderecoCadastrado = cadastroDeEnderecoService.cadastrarEndereco(dadosParaCadastro);
        var uri = uriComponentsBuilder.path("endereco").buildAndExpand(dadosEnderecoCadastrado).toUri();
        return ResponseEntity.created(uri).body(dadosEnderecoCadastrado);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<DadosEndereco>> listarEndereco(@PathVariable String nome) {
        var dadosEnderecoCadastrado = cadastroDeEnderecoService.listarEndereco(nome);
        return ResponseEntity.ok(dadosEnderecoCadastrado);
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletarEnderecos(@PathVariable String nome) {
        cadastroDeEnderecoService.deletarEnderecos(nome);
        return ResponseEntity.noContent().build();
    }

}
