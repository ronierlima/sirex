package br.gov.pe.ses.starter.security.checkerView;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.security.UsuarioSistema;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class UsuarioContext {

	@Inject
	private ExternalContext externalContext;

	public UsuarioContext() {

	}

	public UsuarioSistema getUsuarioLogado() {
		try {
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) externalContext
					.getUserPrincipal();

			if (auth != null && auth.getPrincipal() != null) {
				return (UsuarioSistema) auth.getPrincipal();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Usuario getUsuario() {
		UsuarioSistema usuarioSistema = getUsuarioLogado();
		return usuarioSistema != null ? usuarioSistema.getUsuario() : null;
	}

	public String getSessionId() {
		try {
			return FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
		} catch (Exception e) {
			return null;
		}
	}
}
