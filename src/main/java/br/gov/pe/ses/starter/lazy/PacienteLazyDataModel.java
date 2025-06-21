package br.gov.pe.ses.starter.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.dto.PacienteFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Paciente;
import br.gov.pe.ses.starter.service.interfaces.PacienteService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class PacienteLazyDataModel extends LazyDataModel<Paciente> {

	private static final long serialVersionUID = 1L;

	@Setter
	private PacienteFiltroDTO filtro;

	private final PacienteService pacienteService;

	private List<Paciente> datasource = new ArrayList<>();

	@Override
	public List<Paciente> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

		try {

			if (filtro == null) {
				return this.datasource;
			}

			filtro.setFilterBy(filterBy);
			filtro.setSortBy(sortBy);
			filtro.setFirst(first);
			filtro.setPageSize(pageSize);

			Page<Paciente> buscaPaginada = pacienteService.buscaPaginada(filtro);

			if (buscaPaginada != null) {
				this.setRowCount((int) buscaPaginada.getTotalElements());
			}

			this.datasource.clear();
			this.datasource.addAll(buscaPaginada.getContent());

			if (this.datasource.isEmpty()) {
				UtilMensagens.mensagemWarn("Sem Resultados");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.datasource;

	}

	@Override
	public String getRowKey(Paciente paciente) {
		return paciente.getId().toString();
	}

	@Override
	public Paciente getRowData(String rowKey) {
		for (Paciente paciente : this.datasource) {
			if (paciente.getId().toString().equalsIgnoreCase(rowKey)) {
				return paciente;
			}

		}
		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

}