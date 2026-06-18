package br.com.gardini.msEndereco.infra.adapter;

import br.com.gardini.msEndereco.domain.entity.Log;
import br.com.gardini.msEndereco.domain.repository.LogsRepository;
import br.com.gardini.msEndereco.infra.port.GravaLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GravaLogImpl implements GravaLog {

    private final LogsRepository logsRepository;

    public GravaLogImpl(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void salvarLog(Log log) {
        logsRepository.save(log);
    }

}
