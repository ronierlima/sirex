package br.gov.pe.ses.starter.dto;

import java.util.List;

import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissaoDTO {

	private Long id;

	private boolean marcado;

	private Funcionalidade pai;

	private List<Funcionalidade> filhas;

	public PermissaoDTO(Funcionalidade funcionalidade) {
		this.pai = funcionalidade;
		this.filhas = funcionalidade.getFilhas();
	}

}
