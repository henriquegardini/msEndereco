package br.com.gardini.msEndereco.domain.mapper;

import br.com.gardini.msEndereco.domain.dto.DadosEndereco;
import br.com.gardini.msEndereco.domain.entity.Endereco;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    DadosEndereco enderecoToDadosEndereco(Endereco endereco);

    List<DadosEndereco> enderecoToDadosEnderecos(List<Endereco> endereco);

}
