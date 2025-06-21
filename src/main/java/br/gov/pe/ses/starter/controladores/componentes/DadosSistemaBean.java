package br.gov.pe.ses.starter.controladores.componentes;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.DadosSistema;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.DadosSistemaService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.Setter;

@Component
@ApplicationScoped
public class DadosSistemaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private DadosSistema dadosSistema;

	@Autowired
	private DadosSistemaService dadosSistemaService;

	public DadosSistemaBean() {

	}

	@PostConstruct
	public void inicializar() {
		System.out.println("CRIOU O BEAN E FAZ O SELECT");
		dadosSistema = dadosSistemaService.getConfigPadrao();
	}

	public StreamedContent getLogoPrincipal() {
		return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(dadosSistema.getLogoPrincipal()))
				.contentType("image/svg+xml").build();
	}

	public StreamedContent getRodape() {
		return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(dadosSistema.getLogoRodape()))
				.contentType("image/svg+xml").build();
	}

	public void atualizar() throws NegocioException {
		dadosSistema = dadosSistemaService.atualizar(dadosSistema);
		UtilMensagens.addInfoMessageGrowl("Sucesso", "Operação Realizada");
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
