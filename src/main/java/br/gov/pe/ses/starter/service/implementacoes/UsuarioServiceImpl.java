package br.gov.pe.ses.starter.service.implementacoes;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pe.ses.starter.data.repository.UsuarioRepository;
import br.gov.pe.ses.starter.data.specifications.OrdenacaoUtil;
import br.gov.pe.ses.starter.data.specifications.UsuarioEspecification;
import br.gov.pe.ses.starter.dto.UsuarioFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> login(String login) {
		return usuarioRepository.login(login);
	}

	@Override
	public Page<Usuario> buscaPaginada(UsuarioFiltroDTO filtro) {

		Sort ordenacao = Sort.by(Sort.Order.asc("pessoa.nome"));

		Specification<Usuario> restricoes = UsuarioEspecification.build(filtro);
		ordenacao = OrdenacaoUtil.criar(filtro.getSortBy());

		Pageable page = PageRequest.of(filtro.getQtdRegistros(), filtro.getPageSize(), ordenacao);

		return usuarioRepository.findAll(restricoes, page);

	}

	@Transactional
	@Override
	public Usuario cadastrar(Usuario usuario) throws Exception {

		try {

			if (usuario.isNovo()) {

				usuario.getPessoa().setEmail((usuario.getPessoa().getEmail().toLowerCase()));
				usuario.setLogin(usuario.getLogin().toLowerCase());
				usuario.setAtivo(true);
				usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
				usuario.setUnidade(usuario.getHospitaisAssociados().getFirst());

			}

			Set<Unidade> hospitaisAssociados = usuario.getHospitaisAssociados().stream().collect(Collectors.toSet());

			boolean todosPerfisValidos = usuario.getPerfis().stream()
					.allMatch(perfil -> hospitaisAssociados.contains(perfil.getUnidade()));

			if (!todosPerfisValidos) {
				throw new NegocioException(
						"Atenção! Existem perfis relacionados a hospitais não vinculados ao cadastro do usuário.");

			}

			Unidade h = usuario.getUnidade();
			List<Perfil> perfis = usuario.getPerfis();

			boolean todosOsHospitaisComPerfilCorrespondente = hospitaisAssociados.stream()
					.allMatch(hospital -> perfis.stream().anyMatch(perfil -> perfil.getUnidade().equals(hospital)));

			if (!todosOsHospitaisComPerfilCorrespondente) {
				throw new NegocioException(
						"Atenção! Existe(m) hospital(is) vinculado(s) ao usuário sem perfil de navegação correspondente.");
			}

			boolean hospitalPadraoValido = hospitaisAssociados.stream().anyMatch(p -> p.equals(h));

			if (!hospitalPadraoValido) {
				throw new NegocioException(
						"Atenção! O hospital definido como padrão não corresponde a um hospital associado ao usuário.");
			}

			usuario = usuarioRepository.save(usuario);

			return usuario;

		} catch (org.springframework.orm.ObjectOptimisticLockingFailureException
				| org.hibernate.StaleObjectStateException e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Este registro foi alterado por outro usuário. Por favor, refaça a operação!");
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Erro ao Cadastrar/Atualizar o Usuário.");
		}

	}

	@Override
	public Usuario alterarStatus(Usuario usuario) {

		boolean statusAtual = usuario.getAtivo();
		usuario.setAtivo(!statusAtual);
		usuario = usuarioRepository.save(usuario);
		UtilMensagens.addInfoMessageGrowl("Sucesso!", "Status Alterado com Sucesso!");
		return usuario;
	}

	@Override
	public Usuario porIdComDependencia(Long id) {
		return usuarioRepository.porIdComDependencias(id);

	}

	@Override
	public Usuario porEmail(String email) throws NegocioException {

		Optional<Usuario> optUsuario = usuarioRepository.porEmail(email);

		if (optUsuario.isEmpty()) {
			throw new NegocioException("Usuário Nao Encontrado com o Email!");
		}

		return optUsuario.get();

	}

	@Override
	public int resetToken(Usuario u) {

		try {

			return usuarioRepository.definirResetToken(u.getResetToken(), u.getDataHoraExpToken(), u.getId());

		} catch (Exception e) {

			System.err.println("Erro ao Atualizar Token de Redefinição de Senha!");
			e.printStackTrace();

		}

		return 0;

	}

	@Override
	public Usuario usuarioPorResetToken(String resetToken) throws NegocioException {

		Optional<Usuario> optUsuario = usuarioRepository.usuarioAtivoPorResetToken(resetToken);

		if (optUsuario.isEmpty()) {
			throw new NegocioException("Token Invalido ou Expirado!");
		}

		return optUsuario.get();

	}

	@Override
	public int alterarSenha(Usuario u) throws NegocioException {

		try {

			return usuarioRepository.atualizarSenha(u);

		} catch (Exception e) {

			e.printStackTrace();
			throw new NegocioException("Erro ao Atualizar Senha!");

		}

	}

	@Override
	public int alterarHospitalPadrao(Usuario u) throws NegocioException {

		try {

			return usuarioRepository.atualizarUnidadePadrao(u);

		} catch (Exception e) {

			e.printStackTrace();
			throw new NegocioException("Erro ao Atualizar Hospital Padrão!");

		}

	}

}
