package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;
import br.gov.pe.ses.starter.entidades.publico.Usuario;

public interface UsuarioLogadoService {
	
	public List<Usuario> getUsuariosLogados();
	
	public void invalidarSessaoDoUsuario(Usuario u);

}
