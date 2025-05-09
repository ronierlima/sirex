package br.gov.pe.ses.starter.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.dto.UsuarioDTO;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioAssembler {

	private final ModelMapper modelMapper;

	public UsuarioDTO toDto(Usuario servidor) {
		return modelMapper.map(servidor, UsuarioDTO.class);
	}

	public List<UsuarioDTO> toListDto(List<Usuario> lista) {
		return lista.stream().map(c -> toDto(c)).collect(Collectors.toList());
	}
}
