package br.gov.pe.ses.starter.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	public void preRender(ComponentSystemEvent cse) throws AbortProcessingException {
		
		Map<String, Object> atributos = cse.getComponent().getAttributes();
        String error = (String)atributos.get("error");
        
        if (error != null && !"".equals(error) && "true".equals(request.getParameter("invalid"))) {
        	UtilMensagens.mensagemWarn(error);            
        }
        if ("true".equals(request.getParameter("sessionExpired"))) {
        	UtilMensagens.mensagemWarn("Sua sessão expirou! Por favor, faça login novamente!");        	
        }
        if ("true".equals(request.getParameter("maximumSessions"))) {
        	UtilMensagens.mensagemWarn("Existe outra Sessão Ativa para o Usuário!");           
        }                
    }

	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.xhtml");
		dispatcher.forward(request, response);

		facesContext.responseComplete();
	}
			
}