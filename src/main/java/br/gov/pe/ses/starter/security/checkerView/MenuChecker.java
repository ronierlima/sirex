package br.gov.pe.ses.starter.security.checkerView;

import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.enums.Permissao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class MenuChecker {

	@Inject
	private ExternalContext externalContext;

	public boolean isExibirUsuarios() {
		return Permissao.PERMISSOES_USUARIOS.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExibirPacientes() {
		return Permissao.PERMISSOES_PACIENTES.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExibirPerfis() {
		return Permissao.PERMISSOES_PERFIS.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExibirUnidades() {
		return Permissao.PERMISSOES_UNIDADES.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExibirTiposUnidade() {
		return Permissao.PERMISSOES_TIPOS_UNIDADE.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExibirRelatorios() {
		return Permissao.PERMISSOES_RELATORIOS.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExibirConfiguracoes() {
		return Permissao.PERMISSOES_CONFIGURACOES.stream().anyMatch(p -> externalContext.isUserInRole(p.getRole()));
	}

	public boolean isExportarUsuariosCadastrados() {
		return temPermissao(Permissao.EXPORTAR_USUARIOS_CADASTRADOS);
	}

	public boolean isGerenciarSistema() {
		return externalContext.isUserInRole(Permissao.GERENCIAR_SISTEMA.getRole());
	}

	public boolean isMonitoraSistema() {
		return externalContext.isUserInRole(Permissao.MONITORA_SISTEMA.getRole());
	}

	private boolean temPermissao(Permissao permissao) {
		return externalContext.isUserInRole(permissao.getRole());
	}
}
