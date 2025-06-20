package br.gov.pe.ses.starter.exception;

import lombok.Getter;
import lombok.Setter;

public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private boolean exibeDialogo = false;

	public NegocioException(String msg) {
		super(msg);
	}

	public NegocioException(String msg, boolean exibeDialogo) {
		super(msg);
		this.exibeDialogo = exibeDialogo;
	}
}