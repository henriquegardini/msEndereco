//package br.com.gardini.msEndereco.infra.adapter;
//
//import br.com.gardini.msEndereco.domain.dto.DadosBuscaCep;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestClient;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class BuscaEnderecoPeloCepImplTest {
//
//    @Mock
//    private RestClient.Builder restClientBuilder;
//
//    @Mock
//    private RestClient restClient;
//
//    @Mock
//    private RestClient.RequestHeadersUriSpec<?> requestSpec;
//
//    @Mock
//    private RestClient.ResponseSpec responseSpec;
//
//    @InjectMocks
//    private BuscaEnderecoPeloCepImpl adapter;
//
//    @BeforeEach
//    void setup() {
//        restClient= restClientBuilder.build();
//    }
//
//    @Test
//    void execute_comCepValido_deveRetornarDadosBuscaCep() {
//        var cepBuscado = "01001000";
//        var dadosEsperados = new DadosBuscaCep(
//                "01001000",
//                "Praça da Sé",
//                "",
//                "",
//                "Sé",
//                "São Paulo",
//                "SP",
//                "São Paulo",
//                "Sudeste",
//                "1574408",
//                "",
//                "11",
//                "7107"
//        );
//
//        when(restClient.get().uri(anyString()).headers(any()).accept(any()).retrieve()
//                .body(DadosBuscaCep.class)).thenReturn(dadosEsperados);
//
//        var resultado = adapter.execute(cepBuscado);
//
//        assertNotNull(resultado);
//        assertEquals("01001000", resultado.cep());
//        assertEquals("Praça da Sé", resultado.logradouro());
//        assertEquals("SP", resultado.uf());
//    }
//
//    @Test
//    void execute_deveConstruirUriCorreta() {
//        var cepBuscado = "01001000";
//        var dadosEsperados = new DadosBuscaCep(
//                "01001000",
//                "Praça da Sé",
//                "",
//                "",
//                "Sé",
//                "São Paulo",
//                "SP",
//                "",
//                "",
//                "",
//                "",
//                "",
//                ""
//        );
//        when(responseSpec.body(DadosBuscaCep.class)).thenReturn(dadosEsperados);
//
//        adapter.execute(cepBuscado);
//
//        String uriEsperada = "http://viacep.com.br/ws/01001000/json/";
//        verify(requestSpec).uri(uriEsperada);
//    }
//
//    @Test
//    void execute_deveConfigurarMediaTypeApplicationJson() {
//        var cepBuscado = "01001000";
//        var dadosEsperados = new DadosBuscaCep(
//                "01001000",
//                "Praça da Sé",
//                "",
//                "",
//                "Sé",
//                "São Paulo",
//                "SP",
//                "",
//                "",
//                "",
//                "",
//                "",
//                ""
//        );
//        when(responseSpec.body(DadosBuscaCep.class)).thenReturn(dadosEsperados);
//
//        adapter.execute(cepBuscado);
//
//        verify(requestSpec).accept(MediaType.APPLICATION_JSON);
//    }
//
//    @Test
//    void execute_deveExecutarRetrieveEBody() {
//        var cepBuscado = "01001000";
//        var dadosEsperados = new DadosBuscaCep(
//                "01001000",
//                "Praça da Sé",
//                "",
//                "",
//                "Sé",
//                "São Paulo",
//                "SP",
//                "",
//                "",
//                "",
//                "",
//                "",
//                ""
//        );
//        when(responseSpec.body(DadosBuscaCep.class)).thenReturn(dadosEsperados);
//
//        adapter.execute(cepBuscado);
//
//        verify(requestSpec).retrieve();
//        verify(responseSpec).body(DadosBuscaCep.class);
//    }
//
//    @Test
//    void execute_comCepDiferente_deveRetornarDadosCorrespondentesCep() {
//        var cepBuscado = "12345678";
//        var dadosEsperados = new DadosBuscaCep(
//                "12345678",
//                "Rua Teste",
//                "",
//                "",
//                "Centro",
//                "Guarulhos",
//                "SP",
//                "",
//                "",
//                "",
//                "",
//                "",
//                ""
//        );
//
//        when(responseSpec.body(DadosBuscaCep.class)).thenReturn(dadosEsperados);
//
//        var resultado = adapter.execute(cepBuscado);
//
//        assertEquals("12345678", resultado.cep());
//        assertEquals("Rua Teste", resultado.logradouro());
//        assertEquals("Guarulhos", resultado.localidade());
//        verify(requestSpec).uri(String.format("http://viacep.com.br/ws/%s/json/", cepBuscado));
//    }
//}