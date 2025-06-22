package br.gov.pe.ses.starter.security.checkerView;

import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.enums.Permissao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class RelatorioChecker {

	@Inject
	private ExternalContext externalContext;

	public boolean isPodeEmitirRelatorios() {
		return temPermissao(Permissao.EMITIR_RELATORIOS);
	}

	public boolean isPodeExportarUsuariosCadastrados() {
		return temPermissao(Permissao.EXPORTAR_USUARIOS_CADASTRADOS);
	}

	private boolean temPermissao(Permissao permissao) {
		return externalContext.isUserInRole(permissao.getRole());
	}
}
