package br.gov.pe.ses.starter.service.implementacoes;

import java.util.List;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.data.repository.FuncionalidadeRepository;
import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.FuncionalidadeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionalidadeServiceImpl implements FuncionalidadeService {

	private final FuncionalidadeRepository repository;

	@Override
	public List<Funcionalidade> buscarPrincipais() {
		List<Funcionalidade> principais = repository.buscarPrincipais();
		principais.forEach(p -> p.setFilhas(repository.buscarFilhas(p)));
		return principais;
	}
	
	@Override
	public List<Funcionalidade> buscarTodas() throws NegocioException {
		
		try {
			
			List<Funcionalidade> todas = repository.buscarTodas();		
			return todas;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Erro ao buscar funcionalidades");
		}		
		
	}

}
