package br.gov.pe.ses.starter.entidades.auditoria;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.gov.pe.ses.starter.security.UsuarioSistema;

public class MyRevisionListener implements RevisionListener, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void newRevision(Object revisionEntity) {

		try {

			UsuarioRevEntity usuarioRevEntity = (UsuarioRevEntity) revisionEntity;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication == null || authentication.getPrincipal() == null) {
				return;
			}

			if (authentication.getPrincipal() instanceof UsuarioSistema) {
				UsuarioSistema usuario = (UsuarioSistema) authentication.getPrincipal();
				usuarioRevEntity.setUsuario(usuario.getUsuario().getPessoa().getNome());
				usuarioRevEntity.setUsuarioId(usuario.getUsuario().getId());
				usuarioRevEntity.setData(new Date());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Ocorreu um Erro ao Gerar NewRevision p/ Auditoria!");
		}

	}

}