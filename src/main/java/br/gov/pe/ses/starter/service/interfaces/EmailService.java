package br.gov.pe.ses.starter.service.interfaces;

import br.gov.pe.ses.starter.exception.NegocioException;

public interface EmailService {

	boolean enviaEmailDefinicaoSenhaHtml(String assunto, String textoMensagem, String emailDestinatario) throws NegocioException;

}
