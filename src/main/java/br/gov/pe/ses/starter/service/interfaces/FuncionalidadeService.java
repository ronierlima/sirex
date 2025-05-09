package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;

import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface FuncionalidadeService {
	
	public List<Funcionalidade> buscarPrincipais();

	public List<Funcionalidade> buscarTodas() throws NegocioException;

}
