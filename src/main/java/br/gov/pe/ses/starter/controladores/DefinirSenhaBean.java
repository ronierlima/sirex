package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class DefinirSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String novaSenhaRedefinir;

	private String confNovaSenhaRedefinir;

	private String token;

	@Autowired
	private UsuarioService usuarioService;

	private Usuario usuarioEncontrado;

	public void chamadaDaPagina() {

		try {

			usuarioEncontrado = usuarioService.usuarioPorResetToken(token);

			UtilMensagens.mensagemInfo("Informe os Dados para Redefinição da Senha!");

		} catch (NegocioException e) {
			UtilMensagens.mensagemAposRequest(
					"Link de Redefinição de Senha Inválido! Procure o Administrador do Sistema!",
					FacesMessage.SEVERITY_WARN);
			FacesUtil.redirect("login.xhtml");

		}

	}

	public void alterarSenha() {

		try {

			boolean senhasNaoConferem = !novaSenhaRedefinir.contentEquals(confNovaSenhaRedefinir);

			if (senhasNaoConferem) {
				UtilMensagens.mensagemWarn("Os Campos Senha e Confirmar Senha não Correspondem!");
				return;
			}

			usuarioEncontrado.setSenha(BCrypt.hashpw(confNovaSenhaRedefinir, BCrypt.gensalt()));

			if (usuarioService.alterarSenha(usuarioEncontrado) > 0) {

				UtilMensagens.mensagemAposRequest("Senha Alterada com Sucesso!", FacesMessage.SEVERITY_WARN);
				FacesUtil.redirect("login.xhtml");

				return;

			}

		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Realizar Uma ou Mais Validações!");

		}

	}

	public void validaSenhas() {
		boolean senhasDivergente = !confNovaSenhaRedefinir.contentEquals(novaSenhaRedefinir);
		if (senhasDivergente) {
			UtilMensagens.mensagemWarn("As Senhas Informadas não Conferem!");
			confNovaSenhaRedefinir = null;
		}

	}

}