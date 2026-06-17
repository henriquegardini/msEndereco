package br.com.gardini.msEndereco.domain.service;

import br.com.gardini.msEndereco.domain.entity.Log;
import br.com.gardini.msEndereco.domain.repository.LogsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private LogsRepository logsRepository;

    @InjectMocks
    private LogService logService;

    private Log log;

    @BeforeEach
    void setup() {
        log = new Log(LocalDateTime.now(), "00111222", "INFO");
    }

    @Test
    void salvarLog_deveExecutarComSucesso() {
        logService.salvarLog(log);

        verify(logsRepository, times(1)).save(log);
    }

    @Test
    void salvarLog_deveSalvarLogCorretamente() {
        var solicitacao = "Operação de cadastro executada";
        var resposta = "INSERT";
        var logParaSalvar = new Log(LocalDateTime.now(),solicitacao, resposta);

        logService.salvarLog(logParaSalvar);

        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logsRepository).save(captor.capture());

        var logCapturado = captor.getValue();
        assertEquals(solicitacao, logCapturado.getSolicitacao());
        assertEquals(resposta, logCapturado.getResposta());
    }

    @Test
    void salvarLog_comLogDiferentes() {
        var log1 = new Log(LocalDateTime.now(), "Erro ao buscar CEP", "ERROR");
        var log2 = new Log(LocalDateTime.now(), "Endereço deletado", "DELETE");

        logService.salvarLog(log1);
        logService.salvarLog(log2);

        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logsRepository, times(2)).save(captor.capture());

        var logsCapturados = captor.getAllValues();
        assertEquals(2, logsCapturados.size());
        assertEquals("Erro ao buscar CEP", logsCapturados.get(0).getSolicitacao());
        assertEquals("ERROR", logsCapturados.get(0).getResposta());
        assertEquals("Endereço deletado", logsCapturados.get(1).getSolicitacao());
        assertEquals("DELETE", logsCapturados.get(1).getResposta());
    }

    @Test
    void salvarLog_naoDeveLancarExcecao() {
        assertDoesNotThrow(() -> logService.salvarLog(log));
        verify(logsRepository).save(log);
    }

    @Test
    void salvarLog_comDescricaoVazia() {
        var logVazio = new Log(LocalDateTime.now(), "", "INFO");

        logService.salvarLog(logVazio);

        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logsRepository).save(captor.capture());

        var logCapturado = captor.getValue();
        assertEquals("", logCapturado.getSolicitacao());
        assertEquals("INFO", logCapturado.getResposta());
    }
}