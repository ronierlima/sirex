package br.gov.pe.ses.starter.service.interfaces;

import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;

import java.util.List;

public interface MunicipioService {

    List<Municipio> listarTodos();

    List<Municipio> listarPorGere(Gere gere);

}
