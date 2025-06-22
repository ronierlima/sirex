package br.gov.pe.ses.starter.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.dto.TipoUnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.service.interfaces.TipoUnidadeService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class TipoUnidadeLazyDataModel extends LazyDataModel<TipoUnidade> {

	private static final long serialVersionUID = 1L;

	@Setter
	private TipoUnidadeFiltroDTO filtro;

	private final TipoUnidadeService service;

	private List<TipoUnidade> datasource = new ArrayList<>();

	@Override
	public List<TipoUnidade> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

		try {

			if (filtro == null) {
				return this.datasource;
			}

			filtro.setFilterBy(filterBy);
			filtro.setSortBy(sortBy);
			filtro.setFirst(first);
			filtro.setPageSize(pageSize);

			Page<TipoUnidade> buscaPaginada = service.buscaPaginada(filtro);

			this.datasource.clear();
			if (buscaPaginada != null) {
				this.setRowCount((int) buscaPaginada.getTotalElements());
				this.datasource.addAll(buscaPaginada.getContent());
			}

			if (this.datasource.isEmpty()) {
				UtilMensagens.mensagemWarn("Sem Resultados");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.datasource;

	}

	@Override
	public String getRowKey(TipoUnidade tipoUnidade) {
		return tipoUnidade.getId().toString();
	}

	@Override
	public TipoUnidade getRowData(String rowKey) {
		for (TipoUnidade tipoUnidade : this.datasource) {
			if (tipoUnidade.getId().toString().equalsIgnoreCase(rowKey)) {
				return tipoUnidade;
			}

		}
		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

}