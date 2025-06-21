package br.gov.pe.ses.starter.controladores;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.GerarRelatorio;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import jakarta.enterprise.context.SessionScoped;
import lombok.Data;

@SessionScoped
@Component
@Data
public class ExportarUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private GerarRelatorio gerarRelatorio;

	private StreamedContent documento;

	private List<Usuario> usuarios;

	@Autowired
	private UsuarioService service;

	public void exportar() throws NegocioException {

		usuarios = service.ativos(FacesUtil.getUnidadeSelecionado());

		HashMap<String, Object> parametros = new HashMap<>();

		byte[] relatorio = gerarRelatorio.gerar("usuarios", parametros, usuarios);
		documento = DefaultStreamedContent.builder().name("usuarios_cadastrados.pdf").contentType("application/pdf")
				.stream(() -> new ByteArrayInputStream(relatorio)).build();
	}

}
