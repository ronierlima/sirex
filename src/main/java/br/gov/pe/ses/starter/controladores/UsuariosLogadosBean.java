package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.service.interfaces.UsuarioLogadoService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class UsuariosLogadosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuariosLogados;	
			
	@Autowired
	UsuarioLogadoService usuarioLogadoService;
	
	@PostConstruct
	public void init() {		
		
	 listarUsuariosLogados();		
		
	}	
	
	protected void listarUsuariosLogados() {
		
		try {
			
			usuariosLogados = new ArrayList<Usuario>();
			usuariosLogados = usuarioLogadoService.getUsuariosLogados();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Listar Usuários Logados!");
			
		}
		
	}
	
	public void encerrarSessaoUsuario(Usuario u) {
		
		try {
			
			usuarioLogadoService.invalidarSessaoDoUsuario(u);
			listarUsuariosLogados();
			
		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Encerrar Sessão do Usuário!");
		}		
		
	}
	

}

