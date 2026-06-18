package br.com.gardini.msEndereco.infra.port;

import br.com.gardini.msEndereco.domain.entity.Log;

public interface GravaLog {
    void salvarLog(Log log);
}
