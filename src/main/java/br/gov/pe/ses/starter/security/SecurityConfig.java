package br.gov.pe.ses.starter.security;

import static br.gov.pe.ses.starter.util.Funcionalidades.*;

import javax.security.auth.login.AccountExpiredException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserDetailsServiceImpl userDetailsService;
	
	public static final String[] ENDPOINTS_WHITELIST = {
            "/login.xhtml",
            "/notfound.xhtml",
            "/error.xhtml",
            "/app/**",
            "/acessoNegado.xhtml",
            "/recuperarSenha.xhtml",
            "/definirSenha.xhtml",
            "/layout/**",
            "/resources/**",
            "/static/**",
            "/css/**",
            "/img/**",
            "/js/**",
            "*.css",
            "*.js",            
            "/jakarta.faces.resource/**",
            "/resources/images/**",
            "*.png","*.jpeg","*.jpg","*.svg", //imagens
            "*.eot","*.ttf","*.woff", "*.woff2"//fontes
    };

	@Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
	
	@Bean
	@SuppressWarnings("removal")
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/acessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);
    	
		http.csrf().disable().headers().frameOptions().sameOrigin();
		http.csrf((csrf) -> csrf.disable());
		
		http.sessionManagement(sessionManagement -> sessionManagement
                .sessionFixation(sessionFixation -> sessionFixation.migrateSession())
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/login.xhtml?sessionExpired=true"));
		
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
			.requestMatchers("/paginas/principal.xhtml").authenticated()			
			.requestMatchers("/paginas/usuario/listarUsuarios.xhtml","/paginas/usuario/visualizarUsuario.xhtml").hasAnyRole("USUARIO_VISUALIZAR")
			.requestMatchers("/paginas/usuario/cadastrarUsuario.xhtml").hasAnyRole("USUARIO_INCLUIR", "USUARIO_ALTERAR")
			.requestMatchers("/paginas/perfil/listarPerfis.xhtml").hasAnyRole("PERFIL_VISUALIZAR")
			.requestMatchers("/paginas/perfil/incluirPerfil.xhtml").hasAnyRole("PERFIL_INCLUIR", "PERFIL_ALTERAR")
			.requestMatchers("/paginas/perfil/listarPacientes.xhtml").hasAnyRole("PACIENTE_VISUALIZAR")
			.requestMatchers("/paginas/perfil/incluirPerfil.xhtml").hasAnyRole("PACIENTE_INCLUIR", "PACIENTE_ALTERAR")
			.requestMatchers("/paginas/perfil/listarHospitais.xhtml").hasAnyRole("HOSPITAL_VISUALIZAR")
			.requestMatchers("/paginas/perfil/incluirPerfil.xhtml").hasAnyRole("HOSPITAL_INCLUIR", "HOSPITAL_ALTERAR")	
			
			//CONFIGURACOES
			.requestMatchers("/paginas/configuracao/configurar.xhtml").hasRole(habilitaConfiguracao)
			
			//ESTOQUE GERAL
			.requestMatchers("/paginas/estoqueGeral/listarEstoqueGeral.xhtml").hasAnyRole("ESTOQUE_GERAL_VISUALIZAR")
			.requestMatchers("/paginas/estoqueGeral/visualizarEstoqueGeral.xhtml").hasAnyRole("ESTOQUE_GERAL_VISUALIZAR")
			.requestMatchers("/paginas/estoqueGeral/movimentoEstoqueGeral.xhtml").hasAnyRole("ESTOQUE_GERAL_INCLUIR","ESTOQUE_GERAL_ALTERAR")
			.requestMatchers("/paginas/estoqueGeral/detalheSolicitacaoFarmacia.xhtml").hasAnyRole("ESTOQUE_GERAL_ATENDER_SOLICITACAO")
			.requestMatchers("/paginas/estoqueGeral/listarSolicitacoesFarmacia.xhtml","/paginas/estoqueGeral/visualizarSolicitacaoFarmacia.xhtml").hasAnyRole("ESTOQUE_GERAL_VISUALIZAR_SOLICITACAO")
						
			 //FARMÁCIA
			.requestMatchers("/paginas/farmacia/listarItensEstoqueFarmacia.xhtml").hasAnyRole("ESTOQUE_FARMACIA_VISUALIZAR_ESTOQUE")
			.requestMatchers("/paginas/farmacia/solicitacoesTransferencia.xhtml").hasAnyRole("ESTOQUE_FARMACIA_VISUALIZAR_SOLICITACAO")
			.requestMatchers("/paginas/farmacia/visualizarSolicitacaoEstoque.xhtml").hasAnyRole("ESTOQUE_FARMACIA_VISUALIZAR_SOLICITACAO")
			.requestMatchers("/paginas/farmacia/cadastroSolicitacaoEstoque.xhtml").hasAnyRole("ESTOQUE_FARMACIA_SOLICITAR","ESTOQUE_FARMACIA_ALTERAR_SOLICITACAO")
			.requestMatchers("/paginas/farmacia/liberarProdutos.xhtml").hasAnyRole(liberarEstoqueFarmacia)
			
			//MANUTENÇÃO
			.requestMatchers("/paginas/manutencao/atendimentos/reverterAtendimentos.xhtml").hasRole(manutencaoReverterAtendimentos)
			.requestMatchers("/paginas/manutencao/inventarioFarmacia.xhtml").hasRole(manutencaoInventarioFarmacia)
			.requestMatchers("/paginas/manutencao/inventarioEstoqueGeral.xhtml").hasRole(manutencaoInventarioEstoqueGeral)
			
			//RELATÓRIOS
			.requestMatchers("/paginas/farmacia/relatorios/**").hasRole(emissaoRelatorios)
			.requestMatchers("/paginas/paciente/relatorios/**").hasRole(emissaoRelatorios)
			.requestMatchers("/paginas/administracao/relatorios/relatorioAtividades.xhtml").hasRole(relatorioAtividades)
			
			.anyRequest().authenticated())							
			.formLogin((formLogin) -> formLogin
					.usernameParameter("usuario")
					.passwordParameter("senha")	
					.loginPage("/login.xhtml")
					.loginProcessingUrl("/login.xhtml")
					.failureUrl("/login.xhtml?invalid=true")
					.defaultSuccessUrl("/paginas/principal.xhtml", true))
				
		.logout((logout) -> logout					
					.logoutUrl("/logout").logoutSuccessUrl("/login.xhtml").deleteCookies("remember-me")
					.deleteCookies("JSESSIONID").invalidateHttpSession(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
		
		.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/acessoNegado.xhtml")
                .authenticationEntryPoint(jsfLoginEntry)
                .accessDeniedHandler(jsfDeniedEntry))
		
		.securityContext((securityContext) -> securityContext
				.securityContextRepository(new DelegatingSecurityContextRepository(
					new RequestAttributeSecurityContextRepository(),
					new HttpSessionSecurityContextRepository()
				))
			)
			.authenticationManager(authenticationManager());
		
		return http.build();
	
    }

	@Bean
	AuthenticationManager authenticationManager() {
		
		return authentication -> {

			UserDetails usuario = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
			String senhaDigitada = (String) authentication.getCredentials();
			
			try {

				if (usuario != null && BCrypt.checkpw(senhaDigitada, usuario.getPassword())) {

					if (!usuario.isEnabled()) {

						throw new DisabledException("Usuário com Cadastro Inativo! Procure o Administrador do Sistema.");
						
					} else if (!usuario.isAccountNonLocked() && usuario.isAccountNonExpired()) {

						throw new LockedException(
								"Usuário com Acesso ao Sistema Inativo! Procure o Administrador do Sistema.");

					} else if (!usuario.isAccountNonExpired()) {

						throw new AccountExpiredException(
								"Usuário com Cadastro de Acesso Pendente! Procure o Administrador do Sistema!");

					} else if (usuario.getAuthorities().isEmpty()) {

						throw new BadCredentialsException(
								"Usuário sem Permissões Definidas para Navegação. Procure o Administrador do Sistema.");

					} else {

						return new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(),
								usuario.getAuthorities());

					}

				} else {

					throw new UsernameNotFoundException("Usuário ou Senha Inválido!");

				}

			} catch (Exception e) {
				throw new BadCredentialsException(e.getMessage());

			}

		};

	}
	
	@Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
