package br.gov.pe.ses.starter.service.implementacoes;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.EmailService;
import br.gov.pe.ses.starter.service.interfaces.SenhaUsuarioService;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.SistemaConst;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SenhaUsuarioServiceImpl implements SenhaUsuarioService {

	private final UsuarioService usuarioService;

	private final EmailService emailService;

	protected static final int HORAS_VALIDADE_TOKEN_RESET_SENHA = 2;

	@Override
	public void gerarResetTokenEnviarEmail(Usuario usuarioEncontrado) throws NegocioException {

		try {

			String token = UUID.randomUUID().toString();

			usuarioEncontrado.setResetToken(token);
			usuarioEncontrado.setDataHoraExpToken(LocalDateTime.now().plusHours(HORAS_VALIDADE_TOKEN_RESET_SENHA));

			if (usuarioService.resetToken(usuarioEncontrado) > 0) {

				String assuntoMensagem = "Não Responder - Definição/Redefinição de Senha";
				String textoMensagem = "<a href='" + SistemaConst.urlDominioSistema + "/definirSenha.xhtml?reset_token="
						+ token + "'>Clique aqui</a> para Cadastrar uma Nova Senha.";

				emailService.enviaEmailDefinicaoSenhaHtml(assuntoMensagem, textoMensagem,
						usuarioEncontrado.getPessoa().getEmail());

			} else {

				throw new NegocioException("Erro ao Gerar Link de Redefinição de Senha! Por favor, tente novamente!");

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Erro ao processar sua requisição! Por favor, tente novamente!");
		}

	}

}
