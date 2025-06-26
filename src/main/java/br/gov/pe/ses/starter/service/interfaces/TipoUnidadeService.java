package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.TipoUnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface TipoUnidadeService {

    Page<TipoUnidade> buscaPaginada(TipoUnidadeFiltroDTO filtro);

    TipoUnidade cadastrar(TipoUnidade tipo) throws NegocioException;

    List<TipoUnidade> listarAtivos();

}
