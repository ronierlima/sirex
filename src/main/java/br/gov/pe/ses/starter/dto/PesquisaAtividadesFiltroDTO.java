package br.gov.pe.ses.starter.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.gov.pe.ses.starter.enums.TipoRevisao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PesquisaAtividadesFiltroDTO {

	private UsuarioSimplesDTO usuario;

	private TipoRevisao tipoRevisao;

	private List<LocalDateTime> rangeDatas;	
	
	private String nomeEntidade;
	
	private Long idEntidade; 

}
