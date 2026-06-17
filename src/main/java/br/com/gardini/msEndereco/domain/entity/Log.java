package br.com.gardini.msEndereco.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraProcessamento;
    private String solicitacao;
    private String resposta;

    public Log() {
    }

    public Log(LocalDateTime dataHoraProcessamento, String solicitacao, String resposta) {
        this.dataHoraProcessamento = dataHoraProcessamento;
        this.solicitacao = solicitacao;
        this.resposta = resposta;
    }
}
