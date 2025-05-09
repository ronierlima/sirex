package br.gov.pe.ses.starter.builders;

import java.util.List;

import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Perfil;

public class HospitalBuilder {

	private Hospital hospital;

	public HospitalBuilder(Hospital hospital) {
		this.hospital = hospital;
	}

	public HospitalBuilder comPerfilAdministradorGeral(List<Funcionalidade> funcionalidades) {

		Perfil perfil = new Perfil();

		perfil.setAtivo(true);
		perfil.setNome("Administrador Geral");
		perfil.setHospital(hospital);
		perfil.setFuncionalidades(funcionalidades);

		hospital.getPerfis().add(perfil);

		return this;

	}

	public Hospital construir() {

		this.hospital.setAtivo(true);
		return hospital;

	}

}
