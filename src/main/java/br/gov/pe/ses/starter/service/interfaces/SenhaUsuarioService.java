package br.gov.pe.ses.starter.service.interfaces;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface SenhaUsuarioService {
	
	public void gerarResetTokenEnviarEmail(Usuario usuarioEncontrado) throws NegocioException;
	
}
