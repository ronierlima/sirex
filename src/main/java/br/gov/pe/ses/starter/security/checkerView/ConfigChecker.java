package br.gov.pe.ses.starter.security.checkerView;

import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.enums.Permissao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class ConfigChecker {

	@Inject
	private ExternalContext externalContext;

	public boolean isPpodeGerenciarSistema() {
		return externalContext.isUserInRole(Permissao.GERENCIAR_SISTEMA.getRole());
	}
}
