package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import br.gov.pe.ses.starter.lazy.UnidadeLazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class ListarUnidadesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UnidadeFiltroDTO filtro;

	private Unidade unidadeSelecionada;

	@Autowired
	private UnidadeService unidadeService;

	@Autowired
	private UnidadeLazyDataModel model;

	@Autowired
	private UtilSessionBean utilSessionBean;

	public ListarUnidadesBean() {

	}

	@PostConstruct
	public void inicializar() {
		unidadeSelecionada = new Unidade();
		filtro = new UnidadeFiltroDTO();
		pesquisar();
	}

	public void pesquisar() {
		model.setFiltro(filtro);
	}

	public void alterarStatus() throws NegocioException {
		unidadeService.alterarStatus(unidadeSelecionada);
		inicializar();
	}

	public String alterar() {
		utilSessionBean.addParametro("hospitalSelecionado", unidadeSelecionada);
		return "/paginas/unidade/incluirUnidade.xhtml?faces-redirect=true";
	}

	public String visualizar() {
		utilSessionBean.addParametro("hospitalSelecionado", unidadeSelecionada);
		return "/paginas/unidade/visualizarUnidade.xhtml?faces-redirect=true";
	}
	
	public String incluirHospital() {
		utilSessionBean.limparParametros();
		return "/paginas/unidade/incluirUnidade.xhtml?faces-redirect=true";
	}

}
