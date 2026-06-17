package br.com.gardini.msEndereco.domain.service;

import br.com.gardini.msEndereco.domain.RegraDeNegocioException;
import br.com.gardini.msEndereco.domain.dto.DadosBuscaCep;
import br.com.gardini.msEndereco.domain.dto.DadosCadastroEndereco;
import br.com.gardini.msEndereco.domain.dto.DadosEndereco;
import br.com.gardini.msEndereco.domain.entity.Endereco;
import br.com.gardini.msEndereco.domain.mapper.EnderecoMapper;
import br.com.gardini.msEndereco.domain.repository.EnderecoRepository;
import br.com.gardini.msEndereco.infra.port.BuscaEnderecoPeloCep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroDeEnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private BuscaEnderecoPeloCep buscaEnderecoPeloCep;

    @Mock
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private CadastroDeEnderecoService service;

    @BeforeEach
    void setup() {
        // nenhum setup adicional necessário aqui (todos são mocks)
    }

    @Test
    void cadastrarEndereco_comSucesso() {
        var dadosCadastro = new DadosCadastroEndereco("Evento", "01001000", "123", "Apto 1");
        var dadosBuscaCep = new DadosBuscaCep(
                "01001000",
                "Praça da Sé",
                "",
                "",
                "Sé",
                "São Paulo",
                "SP",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        when(enderecoRepository.existsByNomeIgnoringCaseAndCepIgnoringCaseAndNumeroIgnoringCaseAndComplementoIgnoringCase(
                anyString(), anyString(), anyString(), anyString())).thenReturn(false);

        when(buscaEnderecoPeloCep.execute(dadosCadastro.cep())).thenReturn(dadosBuscaCep);

        when(enderecoRepository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var enderecoMontado = new Endereco(dadosCadastro, dadosBuscaCep);
        var dadosEnderecoEsperado = new DadosEndereco(
                enderecoMontado.getNome(),
                enderecoMontado.getCep(),
                enderecoMontado.getNumero(),
                enderecoMontado.getComplemento(),
                enderecoMontado.getLogradouro(),
                enderecoMontado.getBairro(),
                enderecoMontado.getUf()
        );
        when(enderecoMapper.enderecoToDadosEndereco(any(Endereco.class))).thenReturn(dadosEnderecoEsperado);

        var resultado = service.cadastrarEndereco(dadosCadastro);

        assertNotNull(resultado);
        assertEquals(dadosCadastro.nome(), resultado.nome());
        assertEquals(dadosBuscaCep.logradouro(), resultado.logradouro());
        verify(enderecoRepository).save(any(Endereco.class));
        verify(enderecoMapper).enderecoToDadosEndereco(any(Endereco.class));
    }

    @Test
    void cadastrarEndereco_jaExisteLancandoExcecao() {
        var dadosCadastro = new DadosCadastroEndereco("Evento", "01001000", "123", "Apto 1");

        when(enderecoRepository.existsByNomeIgnoringCaseAndCepIgnoringCaseAndNumeroIgnoringCaseAndComplementoIgnoringCase(
                anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        var ex = assertThrows(RegraDeNegocioException.class, () -> service.cadastrarEndereco(dadosCadastro));
        assertTrue(ex.getMessage().toLowerCase().contains("já cadastrado") || ex.getMessage().toLowerCase().contains("endereco já cadastrado"));
        verify(enderecoRepository, never()).save(any());
    }

    @Test
    void listarEndereco_comSucesso() {
        var dadosCadastro = new DadosCadastroEndereco("Evento", "01001000", "123", "Apto 1");
        var dadosBuscaCep = new DadosBuscaCep(
                "01001000",
                "Praça da Sé",
                "",
                "",
                "Sé",
                "São Paulo",
                "SP",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        var endereco = new Endereco(dadosCadastro, dadosBuscaCep);
        var listaEndereco = List.of(endereco);

        when(enderecoRepository.findByNomeIgnoreCase("Evento")).thenReturn(Optional.of(listaEndereco));

        var dadosEndereco = List.of(new DadosEndereco(
                endereco.getNome(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getUf()
        ));
        when(enderecoMapper.enderecoToDadosEnderecos(listaEndereco)).thenReturn(dadosEndereco);

        var resultado = service.listarEndereco("Evento");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Evento", resultado.get(0).nome());
        verify(enderecoRepository).findByNomeIgnoreCase("Evento");
        verify(enderecoMapper).enderecoToDadosEnderecos(listaEndereco);
    }

    @Test
    void listarEndereco_naoEncontradoLancandoExcecao() {
        when(enderecoRepository.findByNomeIgnoreCase("Inexistente")).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> service.listarEndereco("Inexistente"));
        verify(enderecoRepository).findByNomeIgnoreCase("Inexistente");
    }

    @Test
    void deletarEnderecos_comSucesso() {
        var dadosCadastro = new DadosCadastroEndereco("Evento", "01001000", "123", "Apto 1");
        var dadosBuscaCep = new DadosBuscaCep(
                "01001000",
                "Praça da Sé",
                "",
                "",
                "Sé",
                "São Paulo",
                "SP",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        var endereco1 = new Endereco(dadosCadastro, dadosBuscaCep);
        var endereco2 = new Endereco(dadosCadastro, dadosBuscaCep);
        var lista = List.of(endereco1, endereco2);

        when(enderecoRepository.findByNomeIgnoreCase("Evento")).thenReturn(Optional.of(lista));

        service.deletarEnderecos("Evento");

        verify(enderecoRepository, times(1)).findByNomeIgnoreCase("Evento");
        verify(enderecoRepository, times(2)).delete(any(Endereco.class));

        ArgumentCaptor<Endereco> captor = ArgumentCaptor.forClass(Endereco.class);
        verify(enderecoRepository, times(2)).delete(captor.capture());
        var capturados = captor.getAllValues();
        assertEquals(2, capturados.size());
    }

    @Test
    void deletarEnderecos_naoEncontradoLancandoExcecao() {
        when(enderecoRepository.findByNomeIgnoreCase("Inexistente")).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> service.deletarEnderecos("Inexistente"));
        verify(enderecoRepository).findByNomeIgnoreCase("Inexistente");
        verify(enderecoRepository, never()).delete(any());
    }
}