package br.gov.pe.ses.starter.enums;

import lombok.Getter;

@Getter
public enum PerfilEnum {

	ADMINISTRADOR(1L, "Administrador");

	private final Long id;

	private final String descricao;

	PerfilEnum(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

}
