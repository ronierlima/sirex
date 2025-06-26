package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class VisualizarUnidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Unidade unidade;

	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	@PostConstruct
	public void inicializar() {
		try {
			unidade = (Unidade) utilSessionBean.getParametro("unidadeSelecionada");
			unidade = unidadeService.porIdComDependencias(unidade.getId());
		} catch (Exception e) {
			FacesUtil.redirect("/paginas/unidade/listarUnidades.xhtml?faces-redirect=true");
		}
	}

}
