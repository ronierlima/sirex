package br.gov.pe.ses.starter.events;

import org.springframework.context.ApplicationEvent;

import br.gov.pe.ses.starter.entidades.publico.Unidade;
import lombok.Getter;

public class HospitalCadastradoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	@Getter
	private Unidade hospital;

	public HospitalCadastradoEvent(Unidade hospital) {
		super(hospital);
		this.hospital = hospital;
	}

}
