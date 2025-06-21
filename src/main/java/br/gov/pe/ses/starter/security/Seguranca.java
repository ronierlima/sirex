package br.gov.pe.ses.starter.security;

import static br.gov.pe.ses.starter.util.Funcionalidades.alterarPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.alterarPerfil;
import static br.gov.pe.ses.starter.util.Funcionalidades.alterarUnidade;
import static br.gov.pe.ses.starter.util.Funcionalidades.alterarUsuario;
import static br.gov.pe.ses.starter.util.Funcionalidades.gerenciarSistema;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirPerfil;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirUnidade;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirUsuario;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeConfiguracoes;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeHospital;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDePacientes;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDePerfis;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeRelatorios;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeTiposUnidades;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeUsuarios;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarPerfil;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarUnidade;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarUsuario;
import static br.gov.pe.ses.starter.util.Funcionalidades.exportarUsuariosCadastrados;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.util.SistemaConst;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

@Component
@RequestScoped
public class Seguranca implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ExternalContext externalContext;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		try {

			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) externalContext
					.getUserPrincipal();

			if (auth != null && auth.getPrincipal() != null) {
				usuario = (UsuarioSistema) auth.getPrincipal();
			}
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}

		return usuario;
	}

	public Usuario getUsuario() {
		UsuarioSistema usuarioSistema = null;
		try {

			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) externalContext
					.getUserPrincipal();

			if (auth != null && auth.getPrincipal() != null) {
				usuarioSistema = (UsuarioSistema) auth.getPrincipal();
			}
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}

		return usuarioSistema.getUsuario();
	}

	public boolean isPodeIncluirHospital() {
		return externalContext.isUserInRole(incluirUnidade);
	}

	public boolean isPodeVisualizarHospital() {
		return externalContext.isUserInRole(visualizarUnidade);
	}

	public boolean isPodeAlterarHospital() {
		return externalContext.isUserInRole(alterarUnidade);
	}

	public boolean isExibeMenuTipos() {
		return permissoesDeTiposUnidades.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isExibeMenuUsuarios() {
		return permissoesDeUsuarios.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isExibeMenuPerfis() {
		return permissoesDePerfis.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isExibeMenuPacientes() {
		return permissoesDePacientes.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isExibeMenuHospitais() {
		return permissoesDeHospital.stream().anyMatch(externalContext::isUserInRole);
	}
	
	public boolean isExibeMenuRelatorios() {
		return permissoesDeRelatorios.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isExibeMenuAdministracao() {
		return permissoesDeConfiguracoes.stream().anyMatch(externalContext::isUserInRole);
	}
	
	public boolean isExportaUsuariosCadastrados() {
		return externalContext.isUserInRole(exportarUsuariosCadastrados);
	}

	public boolean isGerenciaSistema() {
		return externalContext.isUserInRole(gerenciarSistema);
	}

	public boolean isIncluiUsuario() {
		return externalContext.isUserInRole(incluirUsuario);
	}

	public boolean isPodeVisualizaUsuario() {
		return externalContext.isUserInRole(visualizarUsuario);
	}

	public boolean isPodeIncluirPerfil() {
		return externalContext.isUserInRole(incluirPerfil);
	}

	public boolean isPodeVisualizarPerfil() {
		return externalContext.isUserInRole(visualizarPerfil);
	}

	public boolean isPodeAlterarPerfil() {
		return externalContext.isUserInRole(alterarPerfil);
	}

	public boolean isPodeAlterarUsuario() {
		return externalContext.isUserInRole(alterarUsuario);
	}

	public boolean isPodeIncluirPaciente() {
		return externalContext.isUserInRole(incluirPaciente);
	}

	public boolean isPodeVisualizarPaciente() {
		return externalContext.isUserInRole(visualizarPaciente);
	}

	public boolean isPodeAlterarPaciente() {
		return externalContext.isUserInRole(alterarPaciente);
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

	public String getSessionId() {
		String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
		return sessionId;
	}

	public void atualizarPermissoes() {

		try {

			userDetailsService.atualizarPermissoesDoUsuario(getUsuarioLogado());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
