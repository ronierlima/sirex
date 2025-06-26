package br.gov.pe.ses.starter.service.implementacoes;

import br.gov.pe.ses.starter.data.repository.GereRepository;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.service.interfaces.GereService;
import br.gov.pe.ses.starter.util.CacheConst;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GereServiceImpl implements GereService {

    private final GereRepository gereRepository;

    @Override
    @Cacheable(value = CacheConst.GERES_CACHE)
    public List<Gere> listarTodos() {
        return gereRepository.listarTodos();
    }

    @Override
    public List<Gere> listarPorMacro(MacroRegiao macroRegiao) {
        return gereRepository.listarPorMacro(macroRegiao);
    }
}
