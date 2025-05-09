package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.UsuarioFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.lazy.UsuarioLazyDataModel;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class ListarUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioFiltroDTO filtro;

	private Usuario usuarioSelecionado;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioLazyDataModel model;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	public ListarUsuariosBean() {

	}

	@PostConstruct
	public void inicializar() {
		usuarioSelecionado = new Usuario();
		filtro = new UsuarioFiltroDTO();
		pesquisar();
	}

	public void pesquisar() {
		model.setFiltro(filtro);
	}

	public void alterarStatus() {
		usuarioService.alterarStatus(usuarioSelecionado);
		inicializar();
	}	
	
	public String alterar() {
		
		UtilMensagens.mensagemAposRequest("Realize a edição do usuário e clique em salvar para concluir!", FacesMessage.SEVERITY_INFO);
		utilSessionBean.addParametro("usuarioSelecionado", usuarioSelecionado);
		
		return "cadastrarUsuario?faces-redirect=true";		
		
	}
	
	public String visualizar() {		
		
		utilSessionBean.addParametro("usuarioVisualizar", usuarioSelecionado);		
		return "visualizarUsuario?faces-redirect=true";		
		
	}
	
	public String incluirUsuario() {
		utilSessionBean.limparParametros();
		return "/paginas/usuario/cadastrarUsuario.xhtml?faces-redirect=true";
	}

}
