package br.gov.pe.ses.starter.controladores.componentes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import jakarta.faces.context.ExternalContext;
import jakarta.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import br.gov.pe.ses.starter.entidades.publico.DadosSistema;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.DadosSistemaService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseId;
import lombok.Data;

@Component
@ApplicationScope
@Data
public class DadosSistemaBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private DadosSistema dadosSistema;

	@Autowired
	private DadosSistemaService dadosSistemaService;

	@Autowired
	private ExternalContext externalContext;

	public DadosSistemaBean() {

	}

	@PostConstruct
	public void inicializar() {
		dadosSistema = dadosSistemaService.getConfigPadrao();
	}

	public StreamedContent getLogoPrincipal() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}

		return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(dadosSistema.getLogoPrincipal()))
				.contentType("image/svg+xml").build();
	}

	public StreamedContent getRodape() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}

		return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(dadosSistema.getLogoRodape()))
				.contentType("image/svg+xml").build();
	}

	public StreamedContent getManual() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}

		return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(dadosSistema.getManual()))
				.contentType("application/pdf").build();
	}

	public void atualizar() throws NegocioException {
		dadosSistema = dadosSistemaService.atualizar(dadosSistema);
		UtilMensagens.addInfoMessageGrowl("Sucesso", "Operação Realizada");
	}

	public void aoAnexarManual(FileUploadEvent event) throws NegocioException {
		dadosSistema.setManual(event.getFile().getContent());
		UtilMensagens.addInfoMessageGrowl("Sucesso", "Documento Anexado");
	}

	public void aoAnexarLogoPrincipal(FileUploadEvent event) throws NegocioException {
		dadosSistema.setLogoPrincipal(event.getFile().getContent());
		UtilMensagens.addInfoMessageGrowl("Sucesso", "Documento Anexado");
	}

	public void aoAnexarRodape(FileUploadEvent event) throws NegocioException {
		dadosSistema.setLogoRodape(event.getFile().getContent());
		UtilMensagens.addInfoMessageGrowl("Sucesso", "Documento Anexado");
	}


}
