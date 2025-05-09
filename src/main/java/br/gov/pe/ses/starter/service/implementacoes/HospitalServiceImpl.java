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

import br.gov.pe.ses.starter.builders.HospitalBuilder;
import br.gov.pe.ses.starter.data.repository.GereRepository;
import br.gov.pe.ses.starter.data.repository.HospitalRepository;
import br.gov.pe.ses.starter.data.repository.MacroRepository;
import br.gov.pe.ses.starter.data.repository.MunicipioRepository;
import br.gov.pe.ses.starter.data.specifications.HospitalEspecification;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.dto.HospitalFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.FuncionalidadeService;
import br.gov.pe.ses.starter.service.interfaces.HospitalService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

	private final HospitalRepository hospitalRepository;

	private final MacroRepository macroRepository;

	private final GereRepository gereRepository;

	private final MunicipioRepository municipioRepository;

	private final FuncionalidadeService funcionalidadeService;

	@Override
	public Page<Hospital> buscaPaginada(HospitalFiltroDTO filtro) {

		Sort ordenacao = Sort.by(Sort.Order.desc("id"));

		Specification<Hospital> restricoes = HospitalEspecification.build(filtro);
		ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

		Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

		return hospitalRepository.findAll(restricoes, page);

	}

	@Override
	@Transactional
	public Hospital cadastrar(Hospital hospital) throws NegocioException {

		try {

			List<Funcionalidade> funcionalidades = new ArrayList<Funcionalidade>();
			funcionalidades = funcionalidadeService.buscarTodas();

			if (hospital.isNovo()) {

				hospital = new HospitalBuilder(hospital).comPerfilAdministradorGeral(funcionalidades).construir();

			}

			hospital = hospitalRepository.save(hospital);

			return hospital;

		} catch (org.springframework.orm.ObjectOptimisticLockingFailureException
				| org.hibernate.StaleObjectStateException e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Este registro foi alterado por outro usuário. Por favor, refaça a operação!");
			return hospital;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Erro ao Cadastrar/Atualizar o Hospital.");
		}
	}

	@Override
	public Hospital alterarStatus(Hospital hospital) throws NegocioException {
		try {
			boolean statusAtual = hospital.getAtivo();
			hospital.setAtivo(!statusAtual);
			hospital = hospitalRepository.save(hospital);
			return hospital;
		} catch (Exception e) {
			throw new NegocioException("Ocorreu um Erro ao Alterar Status.");
		}
	}

	@Override
	public List<Hospital> listarHospitaisAtivos() {
		return hospitalRepository.findAllAtivos();
	}

	@Override
	public Hospital porIdComDependencias(Long id) {
		return hospitalRepository.findById(id).get();
	}

	@Override
	public List<MacroRegiao> listarMacros() {
		return macroRepository.listar();
	}

	@Override
	public List<Gere> listarGeres(MacroRegiao macroRegiao) {
		return gereRepository.listar(macroRegiao);
	}

	@Override
	public List<Municipio> listarMunicipios(Gere gere) {
		return municipioRepository.listar(gere);
	}

	@Override
	@Transactional
	public void alterarConfiguracao(Hospital hospital) throws NegocioException {
		hospitalRepository.save(hospital);
	}

}
