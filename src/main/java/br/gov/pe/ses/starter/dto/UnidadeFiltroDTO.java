package br.gov.pe.ses.starter.dto;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeFiltroDTO {
	
	private int first;
	
	private int pageSize;
	
	private Map<String, SortMeta> sortBy;
	
	private Map<String, FilterMeta> filterBy;
	
	private String nome;

	private Boolean status;

	private List<Long> tipos;

	private List<Long> geres;

	private List<Long> municipios;

	public int getQtdRegistros() {
		return first / pageSize;
	}

}
