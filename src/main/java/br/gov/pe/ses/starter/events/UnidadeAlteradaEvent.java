package br.gov.pe.ses.starter.events;

import br.gov.pe.ses.starter.entidades.publico.Unidade;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UnidadeAlteradaEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	@Getter
	private Unidade hospital;

	public UnidadeAlteradaEvent(Unidade hospital) {
		super(hospital);
		this.hospital = hospital;
	}

}
