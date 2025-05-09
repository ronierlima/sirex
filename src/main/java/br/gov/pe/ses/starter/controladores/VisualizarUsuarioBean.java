package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class VisualizarUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;	

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UtilSessionBean utilSessionBean;
		
	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
		buscarUsuario();
	}
	
	private void buscarUsuario() {
		try {
			usuario = (Usuario) utilSessionBean.getParametro("usuarioVisualizar");
			
			if (!ObjectUtils.isEmpty(usuario)) {
				usuario = usuarioService.porIdComDependencia(usuario.getId());
			}			
						
		} catch (Exception e) {
			e.printStackTrace();
			usuario = new Usuario();
		}
	}
		
}
