package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.dto.TipoUnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.lazy.TipoUnidadeLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
@ViewScoped
@Data
public class ListarTiposBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TipoUnidadeLazyDataModel lazyDataModel;

	private TipoUnidadeFiltroDTO filtro;

	private TipoUnidade tipoSelecionado;

	@PostConstruct
	public void inicializar() {
		filtro = new TipoUnidadeFiltroDTO();
		filtro.setStatus(true);
		pesquisar();

	}

	public void pesquisar() {
		lazyDataModel.setFiltro(filtro);
	}

	public void alterar() throws NegocioException {

	}

}
