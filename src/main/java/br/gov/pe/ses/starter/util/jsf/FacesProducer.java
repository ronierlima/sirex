package br.gov.pe.ses.starter.util.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FacesProducer {

	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	@RequestScoped
	public ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	@Produces
	@RequestScoped
	public HttpServletResponse getHttpServletResponse() {
		return ((HttpServletResponse) getExternalContext().getResponse());
	}
	
	@Produces
	@RequestScoped
	public HttpServletRequest getHttpServletRequest() {
		return ((HttpServletRequest) getExternalContext().getRequest());
	}

}