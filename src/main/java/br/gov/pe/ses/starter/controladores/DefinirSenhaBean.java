package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@ViewScoped
@Data
public class DefinirSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	private String novaSenhaRedefinir;

	private String confNovaSenhaRedefinir;

	private String token;

	@Autowired
	private UsuarioService usuarioService;

	@Getter(value = AccessLevel.PROTECTED)
	@Setter(value = AccessLevel.PROTECTED)
	Optional<Usuario> usuarioEncontrado;

	@PostConstruct
	private void inicializar() {

		usuario = new Usuario();

	}

	public void chamadaDaPagina() {

		try {

			buscarUsuarioPorToken();

			boolean usuarioExiste = usuarioEncontrado.isPresent();
			boolean usuarioInexiste = usuarioEncontrado.isEmpty();
			boolean usuarioInativo = usuarioExiste && !usuarioEncontrado.get().isAtivo();
			boolean tokenDataHoraInvalida = usuarioExiste
					&& LocalDateTime.now().isAfter(usuarioEncontrado.get().getDataHoraExpToken());
			boolean tokenRedefinicaoSenhaInvalido = (token == null || token.trim().contentEquals(""));

			if (usuarioInexiste || tokenRedefinicaoSenhaInvalido) {

				UtilMensagens.mensagemAposRequest(
						"Link de Redefinição de Senha Inválido! Procure o Administrador do Sistema!",
						FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");
				return;

			}

			if (usuarioInativo) {

				UtilMensagens.mensagemAposRequest(
						"Usuário com Pendência Cadastral! Procedimento de Definição/Redefinição de Senha não Autorizado! Procure o Administrador do Sistema!",
						FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");
				return;

			}

			if (tokenDataHoraInvalida) {

				UtilMensagens.mensagemAposRequest("Seu Link de Redefinição de Senha Perdeu a Validade (2 horas)",
						FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");
				return;

			}

			UtilMensagens.mensagemInfo("Informe os Dados para Redefinição da Senha!");

		} catch (Exception e) {

			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Recuperar os Dados para Redefinição de Senha!");

		}

	}

	protected void buscarUsuarioPorToken() {

		usuarioEncontrado = Optional.empty();

		try {

			usuarioEncontrado = usuarioService.usuarioPorResetToken(token);

		} catch (NegocioException e) {

			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Buscar Dados do Usuário!");
		}
	}

	public void alterarSenha() {

		try {

			boolean senhasNaoConferem = !novaSenhaRedefinir.contentEquals(confNovaSenhaRedefinir);
			boolean dadosInformadosNaoConferem = usuarioEncontrado.get().getEmail() == null
					|| usuarioEncontrado.get().getEmail().isEmpty()
					|| !usuarioEncontrado.get().getEmail().contentEquals(usuario.getEmail().trim());

			if (senhasNaoConferem) {

				UtilMensagens.mensagemWarn("Os Campos Senha e Confirmar Senha não Correspondem!");
				return;

			}

			if (dadosInformadosNaoConferem) {

				UtilMensagens.mensagemAposRequest(
						"Os Dados Informados não Conferem! Link de Redefinição de Senha Inválido!",
						FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");
				return;

			}

			/*
			 * if (!new PasswordValidator().isSenhaForte(novaSenhaRedefinir)) {
			 * 
			 * UtilMensagens.
			 * mensagemWarn("A senha informada não preeenche os requisitos mínimos de segurança!"
			 * );
			 * 
			 * }
			 */

			Usuario usuario = new Usuario();
			usuario.setId(usuarioEncontrado.get().getId());
			usuario.setSenha(BCrypt.hashpw(confNovaSenhaRedefinir, BCrypt.gensalt()));

			if (usuarioService.alterarSenha(usuario) > 0) {

				UtilMensagens.mensagemAposRequest("Senha Alterada com Sucesso!", FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");

				return;

			}

		} catch (Exception e) {

			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Realizar Uma ou Mais Validações!");

		} finally {

			usuario = new Usuario();
			novaSenhaRedefinir = null;
			confNovaSenhaRedefinir = null;

		}

	}

	public void validaSenhas() {

		if (!confNovaSenhaRedefinir.contentEquals(novaSenhaRedefinir)) {

			UtilMensagens.mensagemWarn("As Senhas Informadas não Conferem!");
			confNovaSenhaRedefinir = null;

		}

	}

}