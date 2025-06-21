package br.gov.pe.ses.starter.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import br.gov.pe.ses.starter.entidades.publico.DadosSistema;
import lombok.Data;

@Data
public class ImpressaoRelatorioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] logo;

	private String nomeSistema;

	private String arquivoJrxml;

	private HashMap<String, Object> parametros;

	private List<?> lista;

	public ImpressaoRelatorioDTO(DadosSistema dadosSistema) {
		this.logo = dadosSistema.getLogoPrincipal();
		this.nomeSistema = dadosSistema.getNome();
	}

}
