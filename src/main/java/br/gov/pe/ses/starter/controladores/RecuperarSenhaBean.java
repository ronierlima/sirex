package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
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

	public void enviar() throws NegocioException {

		Usuario usuarioEncontrado = usuarioService.porEmail(email.toLowerCase());

		if (usuarioEncontrado.getAtivo() == false) {
			String msg = "Usuário com Cadastro Inativo. Procedimento de Definição/Redefinição de Senha não Autorizado! Procure o Administrador do Sistema!";
			UtilMensagens.mensagemAposRequest(msg, FacesMessage.SEVERITY_WARN);
			FacesUtil.redirect("login.xhtml");
			return;

		}

		senhaUsuarioService.gerarResetTokenEnviarEmail(usuarioEncontrado);

		String msgEnvio = "Acabamos de enviar um link de definição/redefinição para seu e-mail cadastrado, com validade de 2 horas. Caso não tenha recebido verifique também a caixa de SPAM do seu e-mail.";

		UtilMensagens.mensagemAposRequest(msgEnvio, FacesMessage.SEVERITY_WARN);
		FacesUtil.redirect("login.xhtml");

	}

}