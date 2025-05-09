package br.gov.pe.ses.starter.controladores.componentes;

import java.io.Serializable;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;

@Component
@Data
@ApplicationScoped
public class GeralBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String versao;

	private String profile;

	private String tipoAmbiente;

	private List<String> entidades;

	public GeralBean() {

	}

	@PostConstruct
	public void init() {
		obterDadosDoProjeto();
	}

	protected void obterDadosDoProjeto() {

		try {

			Manifest manifest = new Manifest(getClass().getResourceAsStream("/META-INF/MANIFEST.MF"));
			Attributes attributes = manifest.getMainAttributes();

			this.versao = attributes.getValue("Implementation-Version");
			this.profile = attributes.getValue("Spring-Profile");

			if (this.profile == null) {
				this.profile = "local";
			}

			switch (this.profile) {

			case "dev": {
				this.tipoAmbiente = "Ambiente de Homologação";
				break;
			}
			case "local": {
				this.tipoAmbiente = "Ambiente de Desenvolvimento";
				break;
			}
			default:
				this.tipoAmbiente = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}