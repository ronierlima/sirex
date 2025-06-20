package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.HospitalFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.lazy.HospitalLazyDataModel;
import br.gov.pe.ses.starter.service.interfaces.HospitalService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class ListarHospitaisBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private HospitalFiltroDTO filtro;

	private Unidade hospitalSelecionado;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private HospitalLazyDataModel model;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	public ListarHospitaisBean() {

	}

	@PostConstruct
	public void inicializar() {
		hospitalSelecionado = new Unidade();
		filtro = new HospitalFiltroDTO();
		pesquisar();
	}

	public void pesquisar() {
		model.setFiltro(filtro);
	}

	public void alterarStatus() throws NegocioException {
		hospitalService.alterarStatus(hospitalSelecionado);
		inicializar();
	}

	public String alterar() {
		utilSessionBean.addParametro("hospitalSelecionado", hospitalSelecionado);
		return "/paginas/hospital/incluirHospital.xhtml?faces-redirect=true";
	}

	public String visualizar() {
		utilSessionBean.addParametro("hospitalSelecionado", hospitalSelecionado);
		return "/paginas/hospital/visualizarHospital.xhtml?faces-redirect=true";
	}
	
	public String incluirHospital() {
		utilSessionBean.limparParametros();
		return "/paginas/hospital/incluirHospital.xhtml?faces-redirect=true";
	}

}
