package br.gov.pe.ses.starter.service.implementacoes;

import java.util.List;

import br.gov.pe.ses.starter.util.CacheConst;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pe.ses.starter.data.repository.TipoUnidadeRepository;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.data.specifications.TipoUnidadeEspecification;
import br.gov.pe.ses.starter.dto.TipoUnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.TipoUnidadeService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoUnidadeServiceImpl implements TipoUnidadeService {

	private final TipoUnidadeRepository tipoUnidadeRepository;

	@Override
	public Page<TipoUnidade> buscaPaginada(TipoUnidadeFiltroDTO filtro) {

		Sort ordenacao = Sort.by(Sort.Order.desc("id"));

		Specification<TipoUnidade> restricoes = TipoUnidadeEspecification.build(filtro);

		ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

		Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

		return tipoUnidadeRepository.findAll(restricoes, page);
	}

	@Override
	@Transactional
	public TipoUnidade cadastrar(TipoUnidade tipo) throws NegocioException {
		try {

			tipo = tipoUnidadeRepository.save(tipo);

			return tipo;

		} catch (org.springframework.orm.ObjectOptimisticLockingFailureException
				| org.hibernate.StaleObjectStateException e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Este registro foi alterado por outro usuário. Por favor, refaça a operação!");
			return tipo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Erro ao Cadastrar/Atualizar o Tipo de Unidade.", true);
		}
	}

	@Override
	@Cacheable(value = CacheConst.TIPOS_ATIVOS_CACHE)
	public List<TipoUnidade> listarAtivos() {
		return tipoUnidadeRepository.findAllAtivos();
	}

}
