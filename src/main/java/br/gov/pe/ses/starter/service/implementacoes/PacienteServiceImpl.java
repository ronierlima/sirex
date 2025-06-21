package br.gov.pe.ses.starter.service.implementacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pe.ses.starter.data.repository.PacienteRepository;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.data.specifications.PacienteEspecification;
import br.gov.pe.ses.starter.dto.PacienteFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Paciente;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.PacienteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

	private final PacienteRepository pacienteRepository;

	@Override
	public Page<Paciente> buscaPaginada(PacienteFiltroDTO filtro) {

		Sort ordenacao = Sort.by(Sort.Order.asc("pessoa.nome"));

		Specification<Paciente> restricoes = PacienteEspecification.build(filtro);
		ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

		Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

		return pacienteRepository.findAll(restricoes, page);
	}

	@Override
	@Transactional
	public Paciente cadastrar(Paciente paciente) throws NegocioException {
		return pacienteRepository.save(paciente);
	}

	@Override
	public Paciente porId(Long id) {
		return pacienteRepository.findById(id).get();
	}

}
