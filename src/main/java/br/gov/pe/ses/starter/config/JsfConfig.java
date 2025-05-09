package br.gov.pe.ses.starter.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsfConfig {

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("org.omnifaces.SOCKET_ENDPOINT_ENABLED", "false");
			servletContext.setInitParameter("jakarta.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE",
					"true");
			servletContext.setInitParameter("primefaces.THEME", "ultima-indigo");
			servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
			servletContext.setInitParameter("jakarta.faces.PROJECT_STAGE", "Production");
			servletContext.setInitParameter("jakarta.faces.FACELETS_LIBRARIES",
					"/WEB-INF/primefaces-ultima.taglib.xml");
			servletContext.setInitParameter("jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", "true");

		};
	}
}
