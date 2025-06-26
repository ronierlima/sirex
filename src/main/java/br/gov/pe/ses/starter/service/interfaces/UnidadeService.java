package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface UnidadeService {

    Page<Unidade> buscaPaginada(UnidadeFiltroDTO filtro);

    Unidade porIdComDependencias(Long id);

    Unidade cadastrar(Unidade hospital) throws NegocioException;

    void alterarConfiguracao(Unidade hospital) throws NegocioException;

    Unidade alterarStatus(Unidade hospital) throws NegocioException;

    List<Unidade> listarUnidadesAtivas();

    List<MacroRegiao> listarMacros();

}
