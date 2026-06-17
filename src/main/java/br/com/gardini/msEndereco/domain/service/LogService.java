package br.com.gardini.msEndereco.domain.service;

import br.com.gardini.msEndereco.domain.entity.Log;
import br.com.gardini.msEndereco.domain.repository.LogsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogService {

    private final LogsRepository logsRepository;

    public LogService(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void salvarLog(Log log) {
        logsRepository.save(log);
    }

}
