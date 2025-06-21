package br.gov.pe.ses.starter.service.implementacoes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pe.ses.starter.data.repository.DadosSistemaRepository;
import br.gov.pe.ses.starter.entidades.publico.DadosSistema;
import br.gov.pe.ses.starter.service.interfaces.DadosSistemaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DadosSsistemaServiceImpl implements DadosSistemaService {

	private final DadosSistemaRepository repository;

	@Override
	public DadosSistema getConfigPadrao() {
		return repository.findTopByOrderByDataInclusaoDesc();
	}

	@Override
	@Transactional
	public DadosSistema atualizar(DadosSistema dadosSistema) {
		return repository.save(dadosSistema);
	}

}
