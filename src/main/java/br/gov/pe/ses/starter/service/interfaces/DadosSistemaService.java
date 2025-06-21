package br.gov.pe.ses.starter.service.interfaces;

import br.gov.pe.ses.starter.entidades.publico.DadosSistema;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface DadosSistemaService {

	public DadosSistema getConfigPadrao();

	public DadosSistema atualizar(DadosSistema dadosSistema) throws NegocioException;

}
