package br.gov.pe.ses.starter.service.implementacoes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.gov.pe.ses.starter.util.CacheConst;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.pe.ses.starter.builders.UnidadeBuilder;
import br.gov.pe.ses.starter.data.repository.UnidadeRepository;
import br.gov.pe.ses.starter.data.repository.MacroRepository;
import br.gov.pe.ses.starter.data.specifications.UnidadeEspecification;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.FuncionalidadeService;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadeServiceImpl implements UnidadeService {

    private final UnidadeRepository unidadeRepository;

    private final MacroRepository macroRepository;

    private final FuncionalidadeService funcionalidadeService;

    @Override
    public Page<Unidade> buscaPaginada(UnidadeFiltroDTO filtro) {

        Sort ordenacao = Sort.by(Sort.Order.desc("id"));

        Specification<Unidade> restricoes = UnidadeEspecification.build(filtro);
        ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

        Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

        return unidadeRepository.findAll(restricoes, page);

    }

    @Override
    @Transactional
    public Unidade cadastrar(Unidade unidade) {

        try {

            validarUnidade(unidade);

            List<Funcionalidade> funcionalidades;
            funcionalidades = funcionalidadeService.buscarTodas();

            if (unidade.isNovo()) {
                unidade = new UnidadeBuilder(unidade).comPerfilAdministradorGeral(funcionalidades).construir();
            }

            unidade = unidadeRepository.save(unidade);

            return unidade;

        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException
                 | org.hibernate.StaleObjectStateException e) {
            e.printStackTrace();
            UtilMensagens.mensagemError("Este registro foi alterado por outro usuário. Por favor, refaça a operação!");
            return unidade;
        }
    }

    private void validarUnidade(Unidade unidade) throws NegocioException {

        boolean cnpjExiste = unidadeRepository.existsByCnpjAndNotSameId(unidade);

        if (cnpjExiste) {
            throw new NegocioException("Já existe uma unidade cadastrada com esse CNPJ.");
        }

        boolean cnesExiste = unidadeRepository.existsByCnesAndNotSameId(unidade);

        if (Objects.nonNull(unidade.getCnes()) && cnesExiste) {
            throw new NegocioException("Já existe uma unidade cadastrada com esse CNES.");
        }

    }


    @Override
    @Cacheable(value = CacheConst.UNIDADES_ATIVAS_CACHE)
    public List<Unidade> listarUnidadesAtivas() {
        return unidadeRepository.findAllAtivos();
    }

    @Override
    public Unidade porIdComDependencias(Long id) {
        return unidadeRepository.findById(id).get();
    }

    @Override
    public List<MacroRegiao> listarMacros() {
        return macroRepository.listar();
    }

    @Override
    @Transactional
    public void alterarConfiguracao(Unidade hospital) {
        unidadeRepository.save(hospital);
    }

    @Override
    @Transactional
    public void inativarUnidade(Unidade unidade) {

        Unidade unidadeAtual = getById(unidade.getId());

        unidadeAtual.inativar();

        unidadeRepository.save(unidadeAtual);

    }

    @Override
    @Transactional
    public void ativarUnidade(Unidade unidade) {

        Unidade unidadeAtual = getById(unidade.getId());

        unidadeAtual.ativar();

        unidadeRepository.save(unidadeAtual);

    }

    private Unidade getById(Long id) {

        Optional<Unidade> optional = unidadeRepository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Unidade não encontrada com o id: " + id + " .");
        }

        return optional.get();
    }


}
