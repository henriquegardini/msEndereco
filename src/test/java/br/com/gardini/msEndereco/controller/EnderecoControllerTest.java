package br.com.gardini.msEndereco.controller;

import br.com.gardini.msEndereco.domain.RegraDeNegocioException;
import br.com.gardini.msEndereco.domain.dto.DadosCadastroEndereco;
import br.com.gardini.msEndereco.domain.dto.DadosEndereco;
import br.com.gardini.msEndereco.domain.service.CadastroDeEnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerTest {

    @Mock
    private CadastroDeEnderecoService cadastroDeEnderecoService;

    @InjectMocks
    private EnderecoController controller;

    private DadosCadastroEndereco dadosCadastro;
    private DadosEndereco dadosEndereco;

    @BeforeEach
    void setup() {
        dadosCadastro = new DadosCadastroEndereco("Evento", "01001000", "123", "Apto 1");
        dadosEndereco = new DadosEndereco(
                "Evento",
                "01001000",
                "123",
                "Apto 1",
                "Praça da Sé",
                "Sé",
                "SP"
        );
    }

    @Test
    void cadastrar_deveRetornarCreatedComBodyELocation() {
        when(cadastroDeEnderecoService.cadastrarEndereco(dadosCadastro)).thenReturn(dadosEndereco);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<DadosEndereco> response = controller.cadastrar(dadosCadastro, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dadosEndereco, response.getBody());

        assertNotNull(response.getHeaders().getLocation(), "Header Location não deve ser nulo");
        verify(cadastroDeEnderecoService, times(1)).cadastrarEndereco(dadosCadastro);
    }

    @Test
    void listarEndereco_deveRetornarOkELista() {
        var lista = List.of(dadosEndereco);
        when(cadastroDeEnderecoService.listarEndereco("Evento")).thenReturn(lista);

        ResponseEntity<List<DadosEndereco>> response = controller.listarEndereco("Evento");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lista, response.getBody());
        verify(cadastroDeEnderecoService, times(1)).listarEndereco("Evento");
    }

    @Test
    void deletarEnderecos_deveRetornarNoContentEChamarService() {
        doNothing().when(cadastroDeEnderecoService).deletarEnderecos("Evento");

        ResponseEntity<Void> response = controller.deletarEnderecos("Evento");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(cadastroDeEnderecoService, times(1)).deletarEnderecos("Evento");
    }

    @Test
    void listarEndereco_quandoServiceLancarExcecao_devePropagar() {
        when(cadastroDeEnderecoService.listarEndereco("X"))
                .thenThrow(new RegraDeNegocioException("Endereço não encontrado!"));

        assertThrows(RegraDeNegocioException.class, () -> controller.listarEndereco("X"));
        verify(cadastroDeEnderecoService, times(1)).listarEndereco("X");
    }

    @Test
    void cadastrar_quandoServiceLancarExcecao_devePropagar() {
        when(cadastroDeEnderecoService.cadastrarEndereco(dadosCadastro))
                .thenThrow(new RegraDeNegocioException("Endereco já cadastrado"));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        assertThrows(RegraDeNegocioException.class, () -> controller.cadastrar(dadosCadastro, uriBuilder));
        verify(cadastroDeEnderecoService, times(1)).cadastrarEndereco(dadosCadastro);
    }

    @Test
    void deletar_quandoServiceLancarExcecao_devePropagar() {
        doThrow(new RegraDeNegocioException("Endereço não encontrado!"))
                .when(cadastroDeEnderecoService).deletarEnderecos("Inexistente");

        assertThrows(RegraDeNegocioException.class, () -> controller.deletarEnderecos("Inexistente"));
        verify(cadastroDeEnderecoService, times(1)).deletarEnderecos("Inexistente");
    }
}