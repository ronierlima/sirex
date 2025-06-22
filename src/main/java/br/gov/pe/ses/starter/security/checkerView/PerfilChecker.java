package br.gov.pe.ses.starter.security.checkerView;

import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.enums.Permissao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class PerfilChecker {

	@Inject
	private ExternalContext externalContext;

	public boolean isPodeIncluir() {
		return temPermissao(Permissao.INCLUIR_PERFIL);
	}

	public boolean isPodeAlterar() {
		return temPermissao(Permissao.ALTERAR_PERFIL);
	}

	public boolean isPodeExcluir() {
		return temPermissao(Permissao.EXCLUIR_PERFIL);
	}

	public boolean isPodeVisualizar() {
		return temPermissao(Permissao.VISUALIZAR_PERFIL);
	}

	private boolean temPermissao(Permissao permissao) {
		return externalContext.isUserInRole(permissao.getRole());
	}
}
