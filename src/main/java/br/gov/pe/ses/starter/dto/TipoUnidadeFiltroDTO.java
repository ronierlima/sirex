package br.gov.pe.ses.starter.dto;

import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUnidadeFiltroDTO {

	private String descricao;
	
	private Boolean status;

	private int first;

	private int pageSize;

	private Map<String, SortMeta> sortBy;

	private Map<String, FilterMeta> filterBy;

	public int getQtdRegistros() {
		return first / pageSize;
	}

}
