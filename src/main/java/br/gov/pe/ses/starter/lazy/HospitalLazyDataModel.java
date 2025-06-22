package br.gov.pe.ses.starter.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.dto.HospitalFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.service.interfaces.HospitalService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class HospitalLazyDataModel extends LazyDataModel<Unidade> {

	private static final long serialVersionUID = 1L;

	@Setter
	private HospitalFiltroDTO filtro;

	private final HospitalService hospitalService;

	private List<Unidade> datasource = new ArrayList<>();

	@Override
	public List<Unidade> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

		try {

			if (filtro == null) {
				return this.datasource;
			}

			filtro.setFilterBy(filterBy);
			filtro.setSortBy(sortBy);
			filtro.setFirst(first);
			filtro.setPageSize(pageSize);

			Page<Unidade> buscaPaginada = hospitalService.buscaPaginada(filtro);

			this.datasource.clear();
			if (buscaPaginada != null) {
				this.setRowCount((int) buscaPaginada.getTotalElements());
				this.datasource.addAll(buscaPaginada.getContent());
			}

			if (this.datasource.isEmpty()) {
				UtilMensagens.mensagemError("Sem Resultados");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.datasource;

	}

	@Override
	public String getRowKey(Unidade hospital) {
		return hospital.getId().toString();
	}

	@Override
	public Unidade getRowData(String rowKey) {
		for (Unidade hospital : this.datasource) {
			if (hospital.getId().toString().equalsIgnoreCase(rowKey)) {
				return hospital;
			}

		}
		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

}