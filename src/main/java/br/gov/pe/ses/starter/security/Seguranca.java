package br.gov.pe.ses.starter.security;

import static br.gov.pe.ses.starter.util.Funcionalidades.alterarHospital;
import static br.gov.pe.ses.starter.util.Funcionalidades.alterarPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.alterarPerfil;
import static br.gov.pe.ses.starter.util.Funcionalidades.alterarUsuario;
import static br.gov.pe.ses.starter.util.Funcionalidades.atendimentoEmergencial;
import static br.gov.pe.ses.starter.util.Funcionalidades.bolsaTeste;
import static br.gov.pe.ses.starter.util.Funcionalidades.editarDadosSaudePaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.emissaoRelatorios;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueFarmaciaAlterar;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueFarmaciaExcluir;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueFarmaciaIncluir;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueFarmaciaNotificacoes;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueFarmaciaVisualizarEstoque;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueFarmaciaVisualizarSolicitacao;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueGeralAlterar;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueGeralAtenderSolicitacao;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueGeralIncluir;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueGeralNotificacoes;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueGeralVisualizar;
import static br.gov.pe.ses.starter.util.Funcionalidades.estoqueGeralVisualizarSolicitacao;
import static br.gov.pe.ses.starter.util.Funcionalidades.excluirAtendimento;
import static br.gov.pe.ses.starter.util.Funcionalidades.habilitaConfiguracao;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirAtendimento;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirHospital;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirPerfil;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirResponsavelPelaRetirada;
import static br.gov.pe.ses.starter.util.Funcionalidades.incluirUsuario;
import static br.gov.pe.ses.starter.util.Funcionalidades.iniciarAtendimento;
import static br.gov.pe.ses.starter.util.Funcionalidades.liberarEstoqueFarmacia;
import static br.gov.pe.ses.starter.util.Funcionalidades.manutencaoInventarioEstoqueGeral;
import static br.gov.pe.ses.starter.util.Funcionalidades.manutencaoInventarioFarmacia;
import static br.gov.pe.ses.starter.util.Funcionalidades.manutencaoReverterAtendimentos;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDaManutencao;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeEstoqueFarmacia;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeEstoqueGeral;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeHospital;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeInventario;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDePacientes;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDePerfis;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDeUsuarios;
import static br.gov.pe.ses.starter.util.Funcionalidades.permissoesDoAtendimento;
import static br.gov.pe.ses.starter.util.Funcionalidades.relatorioAtividades;
import static br.gov.pe.ses.starter.util.Funcionalidades.transferirPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarAtendimento;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarDadosSaudePaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarHospital;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarPaciente;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarPerfil;
import static br.gov.pe.ses.starter.util.Funcionalidades.visualizarUsuario;

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
public class Seguranca {

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

	public boolean isExibeMenuUsuarios() {
		return permissoesDeUsuarios.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isExibeMenuAtendimento() {
		return permissoesDoAtendimento.stream().anyMatch(externalContext::isUserInRole);
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

	public boolean isPodeConfigurar() {
		return externalContext.isUserInRole(habilitaConfiguracao);
	}

	public boolean isExibeMenuEstoqueGeral() {
		return permissoesDeEstoqueGeral.stream().anyMatch(externalContext::isUserInRole);
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

	public boolean isPodeIncluirResponsavelRetirada() {
		return externalContext.isUserInRole(incluirResponsavelPelaRetirada);
	}

	public boolean isPodeVisualizarPaciente() {
		return externalContext.isUserInRole(visualizarPaciente);
	}

	public boolean isPodeAlterarPaciente() {
		return externalContext.isUserInRole(alterarPaciente);
	}

	public boolean isPodeVisualizarEstoqueGeral() {
		return externalContext.isUserInRole(estoqueGeralVisualizar);
	}

	public boolean isPodeAlterarEstoqueGeral() {
		return externalContext.isUserInRole(estoqueGeralAlterar);
	}

	public boolean isPodeIncluirEstoqueGeral() {
		return externalContext.isUserInRole(estoqueGeralIncluir);
	}

	public boolean isPodeAtenderSolicitacaoFarmacia() {
		return externalContext.isUserInRole(estoqueGeralAtenderSolicitacao);
	}

	public boolean isPodeVisualizarSolicitacaoFarmacia() {
		return externalContext.isUserInRole(estoqueGeralVisualizarSolicitacao);
	}

	public boolean isPodeIncluirHospital() {
		return externalContext.isUserInRole(incluirHospital);
	}

	public boolean isPodeVisualizarHospital() {
		return externalContext.isUserInRole(visualizarHospital);
	}

	public boolean isPodeAlterarHospital() {
		return externalContext.isUserInRole(alterarHospital);
	}

	public boolean isPodeIncluirAtendimento() {
		return externalContext.isUserInRole(incluirAtendimento);
	}

	public boolean isPodeIniciarAtendimento() {
		return externalContext.isUserInRole(iniciarAtendimento);
	}

	public boolean isPodeIniciarEmergencial() {
		return externalContext.isUserInRole(atendimentoEmergencial);
	}

	public boolean isPodeVisualizarAtendimento() {
		return externalContext.isUserInRole(visualizarAtendimento);
	}

	public boolean isPodeLiberarProdutos() {
		return externalContext.isUserInRole(liberarEstoqueFarmacia);
	}

	public boolean isPodeExcluirAtendimento() {
		return externalContext.isUserInRole(excluirAtendimento);
	}

	public boolean isExibeMenuFarmacia() {
		return permissoesDeEstoqueFarmacia.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isPodeVisualizarItensEstoqueFarmacia() {
		return externalContext.isUserInRole(estoqueFarmaciaVisualizarEstoque);
	}

	public boolean isPodeVisualizarSolicitacoesEstoqueFarmacia() {
		return externalContext.isUserInRole(estoqueFarmaciaVisualizarSolicitacao);
	}

	public boolean isPodeSolicitarTransferenciaEstoque() {
		return externalContext.isUserInRole(estoqueFarmaciaIncluir);
	}

	public boolean isPodeAlterarSolicitacao() {
		return externalContext.isUserInRole(estoqueFarmaciaAlterar);
	}

	public boolean isPodeExcluirSolicitacao() {
		return externalContext.isUserInRole(estoqueFarmaciaExcluir);
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

	public boolean isPodeVisualizarNotificacoesEstoqueGeral() {
		return externalContext.isUserInRole(estoqueGeralNotificacoes);
	}

	public boolean isPodeVisualizarNotificacoesEstoqueFarmacia() {
		return externalContext.isUserInRole(estoqueFarmaciaNotificacoes);
	}

	public boolean isPodeVisualizarAlgumaNotificacaoDeEstoque() {
		return externalContext.isUserInRole(estoqueGeralNotificacoes)
				|| externalContext.isUserInRole(estoqueFarmaciaNotificacoes);
	}

	public boolean isPodeVisualizarAlgumEstoque() {
		return externalContext.isUserInRole(estoqueGeralVisualizar)
				|| externalContext.isUserInRole(estoqueFarmaciaNotificacoes);
	}

	public boolean isExibeMenuManutencao() {
		return permissoesDaManutencao.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isPodeReverterAtendimentos() {
		return externalContext.isUserInRole(manutencaoReverterAtendimentos);
	}

	public boolean isExibeMenuInventario() {
		return permissoesDeInventario.stream().anyMatch(externalContext::isUserInRole);
	}

	public boolean isPodeAcessarInventarioFarmacia() {
		return externalContext.isUserInRole(manutencaoInventarioFarmacia);
	}

	public boolean isPodeEmitirRelatorios() {
		return externalContext.isUserInRole(emissaoRelatorios);
	}

	public boolean isPodeVisualizarDadosSaudeDoPaciente() {
		return externalContext.isUserInRole(visualizarDadosSaudePaciente);
	}

	public boolean isNaoPodeVisualizarDadosSaudeDoPaciente() {
		return !externalContext.isUserInRole(visualizarDadosSaudePaciente);
	}

	public boolean isNaoPodeEditarDadosSaudeDoPaciente() {
		return !externalContext.isUserInRole(editarDadosSaudePaciente);
	}

	public boolean isPodeAcessarInventarioEstoqueGeral() {
		return externalContext.isUserInRole(manutencaoInventarioEstoqueGeral);
	}

	public boolean isPodeAcessarRelatorioAtividades() {
		return externalContext.isUserInRole(relatorioAtividades);
	}

	public boolean isPodeCadastrarBolsaTeste() {
		return externalContext.isUserInRole(bolsaTeste);
	}

	public boolean isPodeTransferirPaciente() {
		return externalContext.isUserInRole(transferirPaciente);
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
