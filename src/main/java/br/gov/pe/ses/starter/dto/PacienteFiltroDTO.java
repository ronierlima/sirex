package br.gov.pe.ses.starter.dto;

import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import br.gov.pe.ses.starter.entidades.publico.Unidade;
import lombok.Data;

@Data
public class PacienteFiltroDTO {

	private String nome;

	private String cartaoSus;

	private String prontuario;

	private Boolean status;

	private Unidade unidade;

	private int first;

	private int pageSize;

	private Map<String, SortMeta> sortBy;

	private Map<String, FilterMeta> filterBy;

	public int getQtdRegistros() {
		return first / pageSize;
	}

}
