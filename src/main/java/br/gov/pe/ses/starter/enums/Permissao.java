package br.gov.pe.ses.starter.enums;


import java.util.EnumSet;
import java.util.Set;

public enum Permissao {

    // CONFIGURAÇÕES
    GERENCIAR_SISTEMA("CONFIGURACAO_SISTEMA"),

    // USUÁRIOS
    INCLUIR_USUARIO("USUARIO_INCLUIR"),
    ALTERAR_USUARIO("USUARIO_ALTERAR"),
    EXCLUIR_USUARIO("USUARIO_EXCLUIR"),
    VISUALIZAR_USUARIO("USUARIO_VISUALIZAR"),

    // PERFIS
    INCLUIR_PERFIL("PERFIL_INCLUIR"),
    ALTERAR_PERFIL("PERFIL_ALTERAR"),
    EXCLUIR_PERFIL("PERFIL_EXCLUIR"),
    VISUALIZAR_PERFIL("PERFIL_VISUALIZAR"),

    // TIPOS DE UNIDADE
    INCLUIR_TIPO_UNIDADE("TIPO_UNIDADE_INCLUIR"),
    ALTERAR_TIPO_UNIDADE("TIPO_UNIDADE_ALTERAR"),
    EXCLUIR_TIPO_UNIDADE("TIPO_UNIDADE_EXCLUIR"),
    VISUALIZAR_TIPO_UNIDADE("TIPO_UNIDADE_VISUALIZAR"),

    // UNIDADES
    INCLUIR_UNIDADE("UNIDADE_INCLUIR"),
    ALTERAR_UNIDADE("UNIDADE_ALTERAR"),
    EXCLUIR_UNIDADE("UNIDADE_EXCLUIR"),
    VISUALIZAR_UNIDADE("UNIDADE_VISUALIZAR"),

    // PACIENTES
    INCLUIR_PACIENTE("PACIENTE_INCLUIR"),
    ALTERAR_PACIENTE("PACIENTE_ALTERAR"),
    EXCLUIR_PACIENTE("PACIENTE_EXCLUIR"),
    VISUALIZAR_PACIENTE("PACIENTE_VISUALIZAR"),

    // RELATÓRIOS
    EMITIR_RELATORIOS("RELATORIO"),
    EXPORTAR_USUARIOS_CADASTRADOS("RELATORIO_USUARIOS_CADASTRADOS");

    private final String role;

    Permissao(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return this.role;
    }


    public static final Set<Permissao> PERMISSOES_CONFIGURACOES = EnumSet.of(
        GERENCIAR_SISTEMA
    );

    public static final Set<Permissao> PERMISSOES_USUARIOS = EnumSet.of(
        INCLUIR_USUARIO,
        ALTERAR_USUARIO,
        EXCLUIR_USUARIO,
        VISUALIZAR_USUARIO
    );

    public static final Set<Permissao> PERMISSOES_PERFIS = EnumSet.of(
        INCLUIR_PERFIL,
        ALTERAR_PERFIL,
        EXCLUIR_PERFIL,
        VISUALIZAR_PERFIL
    );

    public static final Set<Permissao> PERMISSOES_TIPOS_UNIDADE = EnumSet.of(
        INCLUIR_TIPO_UNIDADE,
        ALTERAR_TIPO_UNIDADE,
        EXCLUIR_TIPO_UNIDADE,
        VISUALIZAR_TIPO_UNIDADE
    );

    public static final Set<Permissao> PERMISSOES_UNIDADES = EnumSet.of(
        INCLUIR_UNIDADE,
        ALTERAR_UNIDADE,
        EXCLUIR_UNIDADE,
        VISUALIZAR_UNIDADE
    );

    public static final Set<Permissao> PERMISSOES_PACIENTES = EnumSet.of(
        INCLUIR_PACIENTE,
        ALTERAR_PACIENTE,
        EXCLUIR_PACIENTE,
        VISUALIZAR_PACIENTE
    );

    public static final Set<Permissao> PERMISSOES_RELATORIOS = EnumSet.of(
        EMITIR_RELATORIOS,
        EXPORTAR_USUARIOS_CADASTRADOS
    );
}

