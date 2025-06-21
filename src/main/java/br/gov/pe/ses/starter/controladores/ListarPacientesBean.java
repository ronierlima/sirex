package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.dto.PacienteFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Paciente;
import br.gov.pe.ses.starter.lazy.PacienteLazyDataModel;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class ListarPacientesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PacienteLazyDataModel model;

	private PacienteFiltroDTO filtro;

	private Paciente pacienteSelecionado;

	@PostConstruct
	public void inicializar() {
		filtro = new PacienteFiltroDTO();
		filtro.setUnidade(FacesUtil.getUnidadeSelecionado());
		pesquisar();
	}

	public void pesquisar() {
		model.setFiltro(filtro);
	}
}
