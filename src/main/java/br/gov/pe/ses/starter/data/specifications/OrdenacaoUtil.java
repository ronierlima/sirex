package br.gov.pe.ses.starter.data.specifications;

import java.util.Map;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Sort;

public class OrdenacaoUtil {

	public static Sort criar(Map<String, SortMeta> sortBy) {

		boolean ordenarConsulta = !sortBy.isEmpty();

		if (ordenarConsulta) {
			for (Map.Entry<String, SortMeta> entry : sortBy.entrySet()) {
				SortMeta sortMeta = entry.getValue();
				String campoParaOrdenacao = sortMeta.getField();
				boolean ascending = sortMeta.getOrder().equals(SortOrder.ASCENDING);
				if (ascending) {
					return Sort.by(Sort.Order.asc(campoParaOrdenacao));
				} else {
					return Sort.by(Sort.Order.desc(campoParaOrdenacao));
				}
			}
		}

		return Sort.unsorted();
	}
	
	public static Sort criar(Map<String, SortMeta> sortBy, Sort ordenacao) {

		boolean ordenarConsulta = !sortBy.isEmpty();

		if (ordenarConsulta) {
			for (Map.Entry<String, SortMeta> entry : sortBy.entrySet()) {
				SortMeta sortMeta = entry.getValue();
				String campoParaOrdenacao = sortMeta.getField();
				boolean ascending = sortMeta.getOrder().equals(SortOrder.ASCENDING);
				if (ascending) {
					ordenacao = Sort.by(Sort.Order.asc(campoParaOrdenacao));
				} else {
					ordenacao = Sort.by(Sort.Order.desc(campoParaOrdenacao));
				}
			}
		}

		return ordenacao;
	}


}
