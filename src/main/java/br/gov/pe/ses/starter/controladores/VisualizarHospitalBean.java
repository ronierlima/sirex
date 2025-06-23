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
public class VisualizarHospitalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Unidade hospital;

	@Autowired
	private UnidadeService hospitalService;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	@PostConstruct
	public void inicializar() {
		try {
			hospital = (Unidade) utilSessionBean.getParametro("hospitalSelecionado");
			hospital = hospitalService.porIdComDependencias(hospital.getId());
		} catch (Exception e) {
			FacesUtil.redirect("/paginas/unidade/listarUnidades.xhtml?faces-redirect=true");
		}
	}

}
