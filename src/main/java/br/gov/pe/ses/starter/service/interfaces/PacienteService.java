package br.gov.pe.ses.starter.service.interfaces;

import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.PacienteFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Paciente;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface PacienteService {

	public Page<Paciente> buscaPaginada(PacienteFiltroDTO filtro);

	public Paciente cadastrar(Paciente paciente) throws NegocioException;

	public Paciente porId(Long id);

}
