package br.gov.pe.ses.starter.service.implementacoes;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	
	protected final static String IMAGEM_GOVERNO_PE = "https://portal.saude.pe.gov.br/wp-content/uploads/2023/08/GovPERGB.png";
	protected final static String NOME_SISTEMA = "SASPO-PE - Sistema de Atenção à Saúde da Pessoa Ostomizada de Pernambuco";
		
	
	@Override
	public boolean enviaEmailDefinicaoSenhaHtml(String assunto, String textoMensagem, String emailDestinatario) throws NegocioException {

		try {
			
			MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(emailDestinatario);
	        helper.setSubject(assunto);

			String assinatura = "\r\n \r\n Por Favor, não Responder. Este é um email automático do " + NOME_SISTEMA+ "\r\n";

			String html =

					"<html>"

							+ "<h2>"+NOME_SISTEMA+"</h2>" + "<p align='justify'><b>"
							+ textoMensagem + "</b></p><br/>"
							+ "<hr align='center' size='1'/>"
							+ "<h4><p align='justify'><i>" + assinatura + "</i></p></h4>"
							+ "<footer>" +
							"<p align='center'><img src=" + IMAGEM_GOVERNO_PE + " width=\"80px\"></p>" +
							"<table align='center'><tr>"
							+ "<td align='center'>Governo de Pernambuco<br/>"							
							+ "Secretaria de Saúde do Estado de Pernambuco<br/>"							
							+ "</td></tr></table>" +

							"</footer>"

							+ "</html>";
			
			 helper.setText(html, true);
			 
			 mailSender.send(message);
			
			return true;			

		} catch (Exception e) {			
			e.printStackTrace();
			throw new NegocioException("Erro ao enviar Email. Por favor, Tente Novamente!");
		}		

	}
	
}
