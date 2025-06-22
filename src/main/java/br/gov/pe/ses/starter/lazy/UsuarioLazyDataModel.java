package br.gov.pe.ses.starter.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.dto.UsuarioFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class UsuarioLazyDataModel extends LazyDataModel<Usuario> {

	private static final long serialVersionUID = 1L;

	@Setter
	private UsuarioFiltroDTO filtro;

	private final UsuarioService usuarioService;

	private List<Usuario> datasource = new ArrayList<>();

	@Override
	public List<Usuario> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

		try {

			if (filtro == null) {
				return this.datasource;
			}

			filtro.setFilterBy(filterBy);
			filtro.setSortBy(sortBy);
			filtro.setFirst(first);
			filtro.setPageSize(pageSize);

			Page<Usuario> buscaPaginada = usuarioService.buscaPaginada(filtro);

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
	public String getRowKey(Usuario usuario) {
		return usuario.getId().toString();
	}

	@Override
	public Usuario getRowData(String rowKey) {
		for (Usuario usuario : this.datasource) {
			if (usuario.getId().toString().equalsIgnoreCase(rowKey)) {
				return usuario;
			}

		}
		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

}