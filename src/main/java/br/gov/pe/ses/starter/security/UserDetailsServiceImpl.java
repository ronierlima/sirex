package br.gov.pe.ses.starter.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	protected final UsuarioService usuarioService;

	public UsuarioSistema loadUserByUsername(String login) {

		Optional<Usuario> usuario = usuarioService.login(login);

		if (usuario.isPresent()) {
			return new UsuarioSistema(usuario.get(), getPermissoesDoUsuario(usuario.get()));
		}

		throw new UsernameNotFoundException("Usuário ou Senha Inválido!");

	}

	private Collection<? extends GrantedAuthority> getPermissoesDoUsuario(Usuario usuario) {

		if (usuario.getPerfis().isEmpty()) {
			throw new UsernameNotFoundException("Usuário sem perfil de acesso cadastrado!");
		}

		List<SimpleGrantedAuthority> permissoes = new ArrayList<>();

		usuario.getPerfis().stream().filter(perfil -> perfil.getUnidade() != null && perfil.getUnidade().equals(usuario.getUnidade()))
        .forEach(perfil -> {
            perfil.getFuncionalidades().forEach(funcionalidade -> {
                permissoes.add(new SimpleGrantedAuthority(funcionalidade.getNome()));
            });
        });

		return permissoes;

	}
	
	public void atualizarPermissoesDoUsuario(UsuarioSistema usuario) {
        
        List<SimpleGrantedAuthority> permissoes = new ArrayList<>();

        usuario.getUsuario().getPerfis().stream().filter(perfil -> perfil.getUnidade() != null && perfil.getUnidade().equals(usuario.getUsuario().getUnidade()))
        .forEach(perfil -> {
            perfil.getFuncionalidades().forEach(funcionalidade -> {
                permissoes.add(new SimpleGrantedAuthority(funcionalidade.getNome()));
            });
        });
        
        Authentication autenticacaoAtual = SecurityContextHolder.getContext().getAuthentication();
        
		if (autenticacaoAtual != null && autenticacaoAtual.getPrincipal().equals(usuario)) {

			Authentication novaAutenticacao = new UsernamePasswordAuthenticationToken(usuario,
					autenticacaoAtual.getCredentials(), permissoes);

			SecurityContextHolder.getContext().setAuthentication(novaAutenticacao);

		}

	}

}
