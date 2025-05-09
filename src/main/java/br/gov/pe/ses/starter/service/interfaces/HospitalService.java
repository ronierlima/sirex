package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.HospitalFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface HospitalService {

	public Page<Hospital> buscaPaginada(HospitalFiltroDTO filtro);

	public Hospital porIdComDependencias(Long id);

	public Hospital cadastrar(Hospital hospital) throws NegocioException;
	
	public void alterarConfiguracao(Hospital hospital) throws NegocioException;

	public Hospital alterarStatus(Hospital hospital) throws NegocioException;

	public List<Hospital> listarHospitaisAtivos();

	public List<MacroRegiao> listarMacros();
	
	public List<Gere> listarGeres(MacroRegiao macroRegiao);
	
	public List<Municipio> listarMunicipios(Gere gere);

}
