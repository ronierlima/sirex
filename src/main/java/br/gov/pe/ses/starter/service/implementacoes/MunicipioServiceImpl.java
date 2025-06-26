package br.gov.pe.ses.starter.service.implementacoes;

import br.gov.pe.ses.starter.data.repository.GereRepository;
import br.gov.pe.ses.starter.data.repository.MunicipioRepository;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.service.interfaces.GereService;
import br.gov.pe.ses.starter.service.interfaces.MunicipioService;
import br.gov.pe.ses.starter.util.CacheConst;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipioServiceImpl implements MunicipioService {

    private final MunicipioRepository municipioRepository;


    @Override
    @Cacheable(value = CacheConst.MUNICIPIOS_CACHE)
    public List<Municipio> listarTodos() {
        return municipioRepository.listarTodos();
    }

    @Override
    public List<Municipio> listarPorGere(Gere gere) {
        return municipioRepository.listarPorGere(gere);
    }
}
