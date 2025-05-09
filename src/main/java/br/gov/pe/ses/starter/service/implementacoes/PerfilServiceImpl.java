package br.gov.pe.ses.starter.service.implementacoes;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pe.ses.starter.data.repository.PerfilRepository;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.data.specifications.PerfilEspecification;
import br.gov.pe.ses.starter.dto.PerfilFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.PerfilService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

	private final PerfilRepository perfilRepository;

	@Override
	public Page<Perfil> buscaPaginada(PerfilFiltroDTO filtro) {

		Sort ordenacao = Sort.by(Sort.Order.asc("nome"));

		Specification<Perfil> restricoes = PerfilEspecification.build(filtro);
		ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

		Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

		return perfilRepository.findAll(restricoes, page);

	}

	@Override
	@Transactional
	public Perfil cadastrar(Perfil perfil) throws NegocioException {

		if (perfil.getSelecionadas().isEmpty()) {
			throw new NegocioException("Selecione as funcionalidades do perfil.");
		}
		
		try {

			perfil.setFuncionalidades(new ArrayList<>());
			perfil.setFuncionalidades(new ArrayList<>(perfil.getSelecionadas()));
			perfil.setHospital(FacesUtil.getHospitalSelecionado());
			perfil.setAtivo(true);
			perfil = perfilRepository.save(perfil);
			
		} catch (org.springframework.orm.ObjectOptimisticLockingFailureException | org.hibernate.StaleObjectStateException e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Este registro foi alterado por outro usuário. Por favor, refaça a operação!");			
			return perfil;
		} catch (Exception e) {
			e.printStackTrace();			
			throw new NegocioException("Erro ao Cadastrar/Atualizar o Perfil.");
		}

		return perfil;
	}

	@Override
	@Transactional
	public Perfil alterarStatus(Perfil perfil) throws NegocioException {
		try {

			boolean statusAtual = perfil.isAtivo();
			perfil.setAtivo(!statusAtual);
			perfil = perfilRepository.save(perfil);
			return perfil;

		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Erro ao Cadastrar Perfil.");
		}

	}

	@Override
	public Perfil porIdComDependencia(Long id) {
		return perfilRepository.porIdComDependencias(id);
	}

	@Override
	public List<Perfil> listarPerfisAtivos(Hospital hospital) {
		return perfilRepository.findAllAtivos(hospital);
	}

}
