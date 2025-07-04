package br.gov.pe.ses.starter.security;

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

import br.gov.pe.ses.starter.enums.Permissao;
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
            "*.png", "*.jpeg", "*.jpg", "*.svg", //imagens
            "*.eot", "*.ttf", "*.woff", "*.woff2"//fontes
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

        http.csrf().disable()
                .headers().frameOptions().sameOrigin();
        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionFixation(sessionFixation -> sessionFixation.migrateSession())
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/login.xhtml?sessionExpired=true"));

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()

                        .requestMatchers("/paginas/principal.xhtml", "/pdf/**", "/ultima/**").authenticated()

                        .requestMatchers("/paginas/usuario/listarUsuarios.xhtml", "/paginas/usuario/visualizarUsuario.xhtml")
                        .hasAnyRole(Permissao.VISUALIZAR_USUARIO.getRole())

                        .requestMatchers("/paginas/usuario/cadastrarUsuario.xhtml")
                        .hasAnyRole(
                                Permissao.INCLUIR_USUARIO.getRole(),
                                Permissao.ALTERAR_USUARIO.getRole()
                        )

                        .requestMatchers("/monitoring/**", "/actuator/**")
                        .hasAnyRole(Permissao.MONITORA_SISTEMA.getRole())

                        .requestMatchers("/paginas/perfil/listarPerfis.xhtml")
                        .hasAnyRole(Permissao.VISUALIZAR_PERFIL.getRole())

                        .requestMatchers("/paginas/perfil/incluirPerfil.xhtml")
                        .hasAnyRole(
                                Permissao.INCLUIR_PERFIL.getRole(),
                                Permissao.ALTERAR_PERFIL.getRole()
                        )

                        .requestMatchers("/paginas/unidade/listarUnidades.xhtml")
                        .hasAnyRole(Permissao.VISUALIZAR_UNIDADE.getRole())

                        .requestMatchers("/paginas/configuracao/configurar.xhtml")
                        .hasAnyRole(Permissao.GERENCIAR_SISTEMA.getRole())

                        .requestMatchers("/paginas/paciente/listarPacientes.xhtml")
                        .hasAnyRole(Permissao.VISUALIZAR_PACIENTE.getRole())

                        .requestMatchers("/paginas/paciente/incluirPaciente.xhtml")
                        .hasAnyRole(
                                Permissao.INCLUIR_PACIENTE.getRole(),
                                Permissao.ALTERAR_PACIENTE.getRole()
                        )

                        .requestMatchers("/paginas/relatorios/usuarios/exportarUsuariosCadastrados.xhtml")
                        .hasRole(Permissao.EXPORTAR_USUARIOS_CADASTRADOS.getRole())
                        
                        .requestMatchers("/paginas/administracao/usuariosLogados.xhtml")
                        .hasAnyRole(Permissao.lISTAR_USUARIOS_LOGADOS.getRole())

                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> formLogin
                        .usernameParameter("usuario")
                        .passwordParameter("senha")
                        .loginPage("/login.xhtml")
                        .loginProcessingUrl("/login.xhtml")
                        .failureUrl("/login.xhtml?invalid=true")
                        .defaultSuccessUrl("/paginas/principal.xhtml", true))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.xhtml")
                        .deleteCookies("remember-me", "JSESSIONID")
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                )

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/acessoNegado.xhtml")
                        .authenticationEntryPoint(jsfLoginEntry)
                        .accessDeniedHandler(jsfDeniedEntry)
                )

                .securityContext(securityContext -> securityContext
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
