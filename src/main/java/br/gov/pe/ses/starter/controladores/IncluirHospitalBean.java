package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.HospitalService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class IncluirHospitalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Hospital hospital;

	public List<MacroRegiao> macros;

	public List<Gere> geres;

	public List<Municipio> municipios;

	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	@PostConstruct
	public void inicializar() {
		hospital = new Hospital();
		buscarHospital();
	}

	private void buscarHospital() {
		try {
			hospital = (Hospital) utilSessionBean.getParametro("hospitalSelecionado");
			hospital = hospitalService.porIdComDependencias(hospital.getId());
		} catch (Exception e) {
			hospital = new Hospital();
		}

		macros = hospitalService.listarMacros();

		if (hospital.isExistente()) {
			aoSelecionarMacro();
			aoSelecionarGere();
		}
	}

	public void aoSelecionarMacro() {
		MacroRegiao macroSelecionada = hospital.getMunicipio().getGere().getMacroRegiao();
		geres = hospitalService.listarGeres(macroSelecionada);
	}

	public void aoSelecionarGere() {
		Gere gereSelecionada = hospital.getMunicipio().getGere();
		municipios = hospitalService.listarMunicipios(gereSelecionada);
	}

	public void cadastrar() throws NegocioException {
		try {		
		hospitalService.cadastrar(hospital);
		inicializar();
		UtilMensagens.msgInfoAposRequest("Hospital Cadastrado");
		FacesUtil.redirect("/paginas/hospital/listarHospitais.xhtml?faces-redirect=true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void alterarStatus() throws NegocioException {
		hospitalService.alterarStatus(hospital);
		inicializar();
		UtilMensagens.addInfoMessageGrowl("Sucesso", "Status Alterado");
	}

}
