package br.gov.pe.ses.starter.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.gov.pe.ses.starter.entidades.publico.Usuario;

public class UtilUserDetails {

	public static Usuario getUsuarioLogado() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UsuarioSistema usuarioSistema = null;
		
		if (!(authentication.getPrincipal() instanceof UsuarioSistema)) {
			return null;
		}
		
		if (authentication != null && authentication.getPrincipal() != null) {
			usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
			return usuarioSistema.getUsuario();
		}
		
		throw new RuntimeException("Não foi possível obter o usuário logado!");
	}

}