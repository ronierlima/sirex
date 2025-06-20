package br.gov.pe.ses.starter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.gov.pe.ses.starter.entidades.publico.Usuario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getLogin(), usuario.getSenha(), usuario.getAtivo(), true, true, true, authorities);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
