package br.gov.pe.ses.starter.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import br.gov.pe.ses.starter.util.CacheConst;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    javax.cache.CacheManager jCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        Map<String, javax.cache.configuration.Configuration<?, ?>> caches = new HashMap<>();

        javax.cache.configuration.Configuration<Object, Object> defaultConfig = buildCacheConfig(CacheConst.DEFAULT_TTL);
        
        caches.put(CacheConst.UNIDADES_ATIVAS_CACHE, defaultConfig);
        caches.put(CacheConst.TIPOS_ATIVOS_CACHE, defaultConfig);
        caches.put(CacheConst.GERES_CACHE, defaultConfig);
        caches.put(CacheConst.MUNICIPIOS_CACHE, defaultConfig);


        caches.forEach(cacheManager::createCache);

        return cacheManager;
    }

    private javax.cache.configuration.Configuration<Object, Object> buildCacheConfig(int ttlInSeconds) {
        return Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(CacheConst.DEFAULT_ENTRIES).offheap(10, MemoryUnit.MB)
            ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ttlInSeconds)))
             .build()
        );
    }

}
