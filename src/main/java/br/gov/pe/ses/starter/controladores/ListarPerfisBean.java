package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.PerfilFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.lazy.PerfilLazyDataModel;
import br.gov.pe.ses.starter.service.interfaces.PerfilService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class ListarPerfisBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PerfilFiltroDTO filtro;

	private Perfil perfilSelecionado;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private PerfilLazyDataModel model;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	public ListarPerfisBean() {

	}

	@PostConstruct
	public void inicializar() {
		filtro = new PerfilFiltroDTO();
		pesquisar();
	}

	public void pesquisar() {
		model.setFiltro(filtro);
	}

	public void alterarStatus() throws NegocioException {
		perfilService.alterarStatus(perfilSelecionado);
		inicializar();
	}

	public String alterar() {
		utilSessionBean.addParametro("perfilSelecionado", perfilSelecionado);
		return "/paginas/perfil/incluirPerfil.xhtml?faces-redirect=true";
	}
	
	public String visualizar() {
		utilSessionBean.addParametro("perfilSelecionado", perfilSelecionado);
		return "/paginas/perfil/visualizarPerfil.xhtml?faces-redirect=true";
	}
	
	public String incluirPerfil() {
		utilSessionBean.limparParametros();
		return "/paginas/perfil/incluirPerfil.xhtml?faces-redirect=true";
	}

}
