package br.gov.pe.ses.starter.builders;

import java.util.List;

import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.Perfil;

public class UnidadeBuilder {

	private Unidade hospital;

	public UnidadeBuilder(Unidade hospital) {
		this.hospital = hospital;
	}

	public UnidadeBuilder comPerfilAdministradorGeral(List<Funcionalidade> funcionalidades) {

		Perfil perfil = new Perfil();

		perfil.setAtivo(true);
		perfil.setNome("Administrador Geral");
		perfil.setUnidade(hospital);
		perfil.setFuncionalidades(funcionalidades);

		hospital.getPerfis().add(perfil);

		return this;

	}

	public Unidade construir() {

		this.hospital.setAtivo(true);
		return hospital;

	}

}
