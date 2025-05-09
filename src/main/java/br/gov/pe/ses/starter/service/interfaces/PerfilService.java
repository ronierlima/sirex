package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;
import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.PerfilFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface PerfilService {
	
	public Perfil porIdComDependencia(Long id);
	
	public Page<Perfil> buscaPaginada(PerfilFiltroDTO filtro);

	public Perfil cadastrar(Perfil perfil) throws NegocioException;
	
	public Perfil alterarStatus(Perfil perfil) throws NegocioException;
	
	public List<Perfil> listarPerfisAtivos(Hospital hospital);

}
