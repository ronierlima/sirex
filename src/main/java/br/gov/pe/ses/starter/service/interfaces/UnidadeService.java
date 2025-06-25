package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface UnidadeService {

	public Page<Unidade> buscaPaginada(UnidadeFiltroDTO filtro);

	public Unidade porIdComDependencias(Long id);

	public Unidade cadastrar(Unidade hospital) throws NegocioException;
	
	public void alterarConfiguracao(Unidade hospital) throws NegocioException;

	public Unidade alterarStatus(Unidade hospital) throws NegocioException;

	public List<Unidade> listarHospitaisAtivos();

	public List<MacroRegiao> listarMacros();
	
	public List<Gere> listarGeres(MacroRegiao macroRegiao);
	
	public List<Municipio> listarMunicipios(Gere gere);

	public List<Unidade> listarHospitaisAtivosTesteCache();

}
