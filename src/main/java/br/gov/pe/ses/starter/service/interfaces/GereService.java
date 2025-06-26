package br.gov.pe.ses.starter.service.interfaces;

import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;

import java.util.List;

public interface GereService {

    List<Gere> listarTodos();
    List<Gere> listarPorMacro(MacroRegiao macroRegiao);

}
