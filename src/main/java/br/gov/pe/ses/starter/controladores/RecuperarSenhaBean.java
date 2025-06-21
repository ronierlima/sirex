package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.service.interfaces.SenhaUsuarioService;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class RecuperarSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SenhaUsuarioService senhaUsuarioService;

	public void enviar() {

		try {

			Optional<Usuario> usuarioEncontrado = Optional.empty();
			usuarioEncontrado = usuarioService.porEmail(email.toLowerCase());

			boolean usuarioNaoExiste = usuarioEncontrado.isEmpty();
			boolean usuarioNaoPossuiEmail = !usuarioNaoExiste && (usuarioEncontrado.get().getPessoa().getEmail() == null
					|| usuarioEncontrado.get().getPessoa().getEmail().isEmpty());
			boolean emailInformadoDiferenteDoEmailCadastrado = !usuarioNaoExiste
					&& !usuarioEncontrado.get().getPessoa().getEmail().contentEquals(email.trim());

			if (usuarioNaoExiste || usuarioNaoPossuiEmail || emailInformadoDiferenteDoEmailCadastrado) {

				UtilMensagens.mensagemWarn(
						"Nenhum Usuário Encontrado com os Dados Informados! Se Você já Possui Cadastro, Procure o Administrador do Sistema!");
				return;

			}

			if (usuarioEncontrado.get().getAtivo() == false) {

				UtilMensagens.mensagemAposRequest(
						"Usuário com Cadastro Inativo. Procedimento de Definição/Redefinição de Senha não Autorizado! Procure o Administrador do Sistema!",
						FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");
				return;

			}

			senhaUsuarioService.gerarResetTokenEnviarEmail(usuarioEncontrado.get());

			UtilMensagens.mensagemAposRequest(
					"Acabamos de enviar um link de definição/redefinição para seu e-mail cadastrado, com validade de 2 horas. Caso não tenha recebido verifique também a caixa de SPAM do seu e-mail.",
					FacesMessage.SEVERITY_WARN);
			FacesUtil.redirect("login.xhtml");

			usuarioEncontrado = Optional.empty();

		} catch (Exception e) {

			e.printStackTrace();
			UtilMensagens.mensagemError(
					"Erro ao Enviar Email de Definição/Redefinição de Senha! Por favor, Tente Novamente!");

		}

	}

}