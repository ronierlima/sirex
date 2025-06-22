package br.gov.pe.ses.starter.security.checkerView;

import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.security.UsuarioSistema;
import br.gov.pe.ses.starter.util.SistemaConst;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class Seguranca {

	@Inject
	private UsuarioContext usuarioContext;

	public UsuarioSistema getUsuarioLogado() {
		return usuarioContext.getUsuarioLogado();
	}

	public Usuario getUsuario() {
		return usuarioContext.getUsuario();
	}

	public String getSessionId() {
		return usuarioContext.getSessionId();
	}

	public String getCanal() {
		return SistemaConst.canalEndpoint;
	}

	public String getWsEndpoint() {
		return SistemaConst.wsRoot + SistemaConst.wsEndpoint;
	}

	public String getWsName() {
		return SistemaConst.wsName;
	}

}
