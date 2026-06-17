package br.com.gardini.msEndereco.domain.repository;

import br.com.gardini.msEndereco.domain.entity.Endereco;
import br.com.gardini.msEndereco.domain.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogsRepository extends JpaRepository<Log, Long> {
}
