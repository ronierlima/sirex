package br.gov.pe.ses.starter.service.implementacoes;

import java.util.ArrayList;
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
import br.gov.pe.ses.starter.builders.UnidadeBuilder;
import br.gov.pe.ses.starter.data.repository.GereRepository;
import br.gov.pe.ses.starter.data.repository.UnidadeRepository;
import br.gov.pe.ses.starter.data.repository.MacroRepository;
import br.gov.pe.ses.starter.data.repository.MunicipioRepository;
import br.gov.pe.ses.starter.data.specifications.UnidadeEspecification;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.FuncionalidadeService;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadeServiceImpl implements UnidadeService {

    private final UnidadeRepository hospitalRepository;

    private final MacroRepository macroRepository;

    private final FuncionalidadeService funcionalidadeService;

    @Override
    public Page<Unidade> buscaPaginada(UnidadeFiltroDTO filtro) {

        Sort ordenacao = Sort.by(Sort.Order.desc("id"));

        Specification<Unidade> restricoes = UnidadeEspecification.build(filtro);
        ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

        Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

        return hospitalRepository.findAll(restricoes, page);

    }

    @Override
    @Transactional
    public Unidade cadastrar(Unidade hospital) throws NegocioException {

        try {

            List<Funcionalidade> funcionalidades;
            funcionalidades = funcionalidadeService.buscarTodas();

            if (hospital.isNovo()) {

                hospital = new UnidadeBuilder(hospital).comPerfilAdministradorGeral(funcionalidades).construir();

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
    public Unidade alterarStatus(Unidade hospital) throws NegocioException {
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
    @Cacheable(value = CacheConst.UNIDADES_ATIVAS_CACHE)
    public List<Unidade> listarUnidadesAtivas() {
        return hospitalRepository.findAllAtivos();
    }

    @Override
    public Unidade porIdComDependencias(Long id) {
        return hospitalRepository.findById(id).get();
    }

    @Override
    public List<MacroRegiao> listarMacros() {
        return macroRepository.listar();
    }

    @Override
    @Transactional
    public void alterarConfiguracao(Unidade hospital) throws NegocioException {
        hospitalRepository.save(hospital);
    }



}
