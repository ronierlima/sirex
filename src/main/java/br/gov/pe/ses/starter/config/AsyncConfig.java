package br.gov.pe.ses.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
