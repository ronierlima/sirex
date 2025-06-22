package br.gov.pe.ses.starter.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.dto.PerfilFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.service.interfaces.PerfilService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class PerfilLazyDataModel extends LazyDataModel<Perfil> {

	private static final long serialVersionUID = 1L;

	@Setter
	private PerfilFiltroDTO filtro;

	private final PerfilService perfilService;

	private List<Perfil> datasource = new ArrayList<>();

	@Override
	public List<Perfil> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

		try {

			if (filtro == null) {
				return this.datasource;
			}

			filtro.setFilterBy(filterBy);
			filtro.setSortBy(sortBy);
			filtro.setFirst(first);
			filtro.setPageSize(pageSize);

			Page<Perfil> buscaPaginada = perfilService.buscaPaginada(filtro);

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
	public String getRowKey(Perfil hospital) {
		return hospital.getId().toString();
	}

	@Override
	public Perfil getRowData(String rowKey) {

		for (Perfil perfil : this.datasource) {
			if (perfil.getId().toString().equalsIgnoreCase(rowKey)) {
				return perfil;
			}
		}

		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

}