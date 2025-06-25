package br.gov.pe.ses.starter.service.implementacoes;

import javax.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.pe.ses.starter.service.interfaces.CacheService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
	
	@Autowired
    private CacheManager cacheManager;

	@Override
    public void limparCache(String nome) {
        javax.cache.Cache<?, ?> cache = cacheManager.getCache(nome);
        if (cache != null) {
            cache.clear();
        }
    }

	@Override
    public void limparTodosCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheManager.getCache(cacheName).clear();
        }
    }

}
