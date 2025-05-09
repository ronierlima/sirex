package br.gov.pe.ses.starter.util;

import java.util.Set;

public class Funcionalidades {

	// CONFIGURACAO
	public static final String habilitaConfiguracao = "ALTERA_CONFIGURACAO";
	
	public static Set<String> permissoesDaConfiguracao = Set.of(habilitaConfiguracao);

	// ATENDIMENTO
	public static final String iniciarAtendimento = "INICIAR_ATENDIMENTO";

	public static final String visualizarAtendimento = "VISUALIZAR_ATENDIMENTO";

	public static final String alterarAtendimento = "ALTERAR_ATENDIMENTO";

	public static final String excluirAtendimento = "EXCLUIR_ATENDIMENTO";

	public static final String atendimentoEmergencial = "INICIAR_ATENDIMENTO_EMERGENGIAL";

	public static final String incluirAtendimento = "INCLUIR_ATENDIMENTO";

	public final static Set<String> permissoesDoAtendimento = Set.of(iniciarAtendimento, visualizarAtendimento,
			alterarAtendimento, excluirAtendimento, atendimentoEmergencial, incluirAtendimento);

	// USUARIOS
	public static final String incluirUsuario = "USUARIO_INCLUIR";

	public static final String alterarUsuario = "USUARIO_ALTERAR";

	public static final String excluirUsuario = "USUARIO_EXCLUIR";

	public static final String visualizarUsuario = "USUARIO_VISUALIZAR";

	public static Set<String> permissoesDeUsuarios = Set.of(incluirUsuario, alterarUsuario, excluirUsuario,
			visualizarUsuario);

	// PERFIS
	public static final String incluirPerfil = "PERFIL_INCLUIR";

	public static final String alterarPerfil = "PERFIL_ALTERAR";

	public static final String excluirPerfil = "PERFIL_EXCLUIR";

	public static final String visualizarPerfil = "PERFIL_VISUALIZAR";

	public static Set<String> permissoesDePerfis = Set.of(incluirPerfil, alterarPerfil, excluirPerfil,
			visualizarPerfil);

	// PACIENTES
	public static final String incluirPaciente = "PACIENTE_INCLUIR";

	public static final String alterarPaciente = "PACIENTE_ALTERAR";

	public static final String excluirPaciente = "PACIENTE_EXCLUIR";

	public static final String visualizarPaciente = "PACIENTE_VISUALIZAR";
	
	public static final String visualizarDadosSaudePaciente = "PACIENTE_VISUALIZAR_DADOS_SAUDE";
	
	public static final String editarDadosSaudePaciente = "PACIENTE_EDITAR_DADOS_SAUDE";
	
	public static final String bolsaTeste = "PACIENTE_BOLSA_TESTE";
	
	public static final String transferirPaciente = "PACIENTE_TRANSFERENCIA";
	
	public static Set<String> permissoesDePacientes = Set.of(incluirPaciente,alterarPaciente,excluirPaciente,visualizarPaciente,transferirPaciente);

	// HOSPITAIS
	public static final String incluirHospital = "HOSPITAL_INCLUIR";

	public static final String alterarHospital = "HOSPITAL_ALTERAR";

	public static final String excluirHospital = "HOSPITAL_EXCLUIR";

	public static final String visualizarHospital = "HOSPITAL_VISUALIZAR";

	public static final Set<String> permissoesDeHospital = Set.of(incluirHospital, alterarHospital, excluirHospital,
			visualizarHospital);

	// ESTOQUE GERAL
	public static final String estoqueGeral = "ESTOQUE_GERAL";

	public static final String estoqueGeralVisualizar = "ESTOQUE_GERAL_VISUALIZAR";

	public static final String estoqueGeralIncluir = "ESTOQUE_GERAL_INCLUIR";

	public static final String estoqueGeralAlterar = "ESTOQUE_GERAL_ALTERAR";

	public static final String estoqueGeralAtenderSolicitacao = "ESTOQUE_GERAL_ATENDER_SOLICITACAO";

	public static final String estoqueGeralVisualizarSolicitacao = "ESTOQUE_GERAL_VISUALIZAR_SOLICITACAO";
	
	public static final String estoqueGeralNotificacoes = "ESTOQUE_GERAL_NOTIFICACOES";

	public static final Set<String> permissoesDeEstoqueGeral = Set.of(estoqueGeral, estoqueGeralVisualizar,
			estoqueGeralIncluir, estoqueGeralAlterar, estoqueGeralAtenderSolicitacao,
			estoqueGeralVisualizarSolicitacao,estoqueGeralNotificacoes);

	// FARMACIA
	public static final String estoqueFarmacia = "ESTOQUE_FARMACIA";

	public static final String liberarEstoqueFarmacia = "LIBERAR_ESTOQUE_FARMACIA";

	public static final String estoqueFarmaciaVisualizarEstoque = "ESTOQUE_FARMACIA_VISUALIZAR_ESTOQUE";

	public static final String estoqueFarmaciaIncluir = "ESTOQUE_FARMACIA_SOLICITAR";

	public static final String estoqueFarmaciaVisualizarSolicitacao = "ESTOQUE_FARMACIA_VISUALIZAR_SOLICITACAO";

	public static final String estoqueFarmaciaAlterar = "ESTOQUE_FARMACIA_ALTERAR_SOLICITACAO";

	public static final String estoqueFarmaciaExcluir = "ESTOQUE_FARMACIA_EXCLUIR_SOLICITACAO";
	
	public static final String estoqueFarmaciaNotificacoes = "ESTOQUE_FARMACIA_NOTIFICACOES";
	
	public static final String incluirResponsavelPelaRetirada = "ESTOQUE_FARMACIA_RESP_RETIRADA";

	public static final Set<String> permissoesDeEstoqueFarmacia = Set.of(estoqueFarmacia,
			estoqueFarmaciaVisualizarEstoque, estoqueFarmaciaVisualizarSolicitacao, estoqueFarmaciaIncluir,
			estoqueFarmaciaAlterar, estoqueFarmaciaExcluir, liberarEstoqueFarmacia,estoqueFarmaciaNotificacoes);
	
	//MANUTENÇÃO
	public static final String manutencao = "MANUTENCAO";
	
	public static final String manutencaoReverterAtendimentos = "MANUTENCAO_REVERTER_ATENDIMENTOS";
	
	public static final String manutencaoInventarioFarmacia = "MANUTENCAO_INVENTARIO_FARMACIA";
	
	public static final String manutencaoInventarioEstoqueGeral = "MANUTENCAO_INVENTARIO_GERAL";
		
	public static Set<String> permissoesDaManutencao = Set.of(manutencao,manutencaoReverterAtendimentos,manutencaoInventarioFarmacia,manutencaoInventarioEstoqueGeral);
	
	public static Set<String> permissoesDeInventario = Set.of(manutencaoInventarioFarmacia,manutencaoInventarioEstoqueGeral);
	
	//RELATORIO
	public static final String emissaoRelatorios = "RELATORIO";
	
	public static final String relatorioAtividades = "RELATORIO_ATIVIDADES";
		
}
