package br.gov.pe.ses.starter.util;

import java.util.Set;

public class Funcionalidades {

	// CONFIGURACOES
	public static final String gerenciarSistema = "CONFIGURACAO_SISTEMA";

	public static Set<String> permissoesDeConfiguracoes = Set.of(gerenciarSistema);

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

	// TIPO UNIDADE
	public static final String incluirTipo = "TIPO_UNIDADE_INCLUIR";

	public static final String alterarTipo = "TIPO_UNIDADE_ALTERAR";

	public static final String excluirTipo = "TIPO_UNIDADE__EXCLUIR";

	public static final String visualizarTipo = "TIPO_UNIDADE__VISUALIZAR";

	public static final Set<String> permissoesDeTiposUnidades = Set.of(incluirTipo, alterarTipo, excluirTipo,
			visualizarTipo);

	// UNIDADES
	public static final String incluirUnidade = "UNIDADE_INCLUIR";

	public static final String alterarUnidade = "UNIDADE_ALTERAR";

	public static final String excluirUnidade = "UNIDADE__EXCLUIR";

	public static final String visualizarUnidade = "UNIDADE__VISUALIZAR";

	public static final Set<String> permissoesDeHospital = Set.of(incluirUnidade, alterarUnidade, excluirUnidade,
			visualizarUnidade);

	// PACIENTES
	public static final String incluirPaciente = "PACIENTE_INCLUIR";

	public static final String alterarPaciente = "PACIENTE_ALTERAR";

	public static final String excluirPaciente = "PACIENTE_EXCLUIR";

	public static final String visualizarPaciente = "PACIENTE_VISUALIZAR";

	public static Set<String> permissoesDePacientes = Set.of(incluirPaciente, alterarPaciente, excluirPaciente,
			visualizarPaciente);

	// RELATORIO
	public static final String emissaoRelatorios = "RELATORIO";

	public static final String exportarUsuariosCadastrados = "RELATORIO_USUARIOS_CADASTRADOS";

	public static Set<String> permissoesDeRelatorios = Set.of(emissaoRelatorios, exportarUsuariosCadastrados);

}
