package br.gov.pe.ses.starter.util.error;

import java.io.IOException;
import java.util.Iterator;

import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.faces.FacesException;
import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

@SuppressWarnings("deprecation")
public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable exception = context.getException();

			exception.printStackTrace();

			NegocioException negocioException = getNegocioException(exception);

			boolean handled = false;

			try {
				if (exception instanceof ViewExpiredException) {
					handled = true;
					redirect("/");
				} else if (negocioException != null) {
					handled = true;

					if (negocioException.isExibeDialogo()) {
						UtilMensagens.msgInDialog("Atenção", negocioException.getMessage());
					} else {
						UtilMensagens.addErrorMessageGrowl("Erro", negocioException.getMessage());
					}

				} else {

					if (exception.getCause() != null) {
						FacesContext fc = FacesContext.getCurrentInstance();
						Flash flash = fc.getExternalContext().getFlash();
						flash.put("message", exception.getCause().getMessage());
						flash.put("type", exception.getCause().getClass().getSimpleName());
						flash.put("exception", exception.getCause().getClass().getName());
					}

					handled = true;
					redirect("/error.xhtml");
				}
			} finally {
				if (handled) {
					events.remove();
				}
			}
		}

		getWrapped().handle();
	}

	private NegocioException getNegocioException(Throwable exception) {
		if (exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if (exception.getCause() != null) {
			return getNegocioException(exception.getCause());
		}

		return null;
	}

	private void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();

			externalContext.redirect(contextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}