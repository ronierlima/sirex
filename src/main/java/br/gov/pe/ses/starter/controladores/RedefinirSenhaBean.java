package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.security.UtilUserDetails;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import br.gov.pe.ses.starter.validators.PasswordValidator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@RequestScoped
@Data
public class RedefinirSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String senhaAtualRedefinir;
	
	private String novaSenhaRedefinir;
	
	private String confNovaSenhaRedefinir;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordValidator passwordValidator;
	
	@Autowired
	@Getter(value = AccessLevel.PROTECTED)
	@Setter(value = AccessLevel.PROTECTED)
	HttpSession session;
	
	@PostConstruct
	private void inicializar() {
		
		senhaAtualRedefinir = null;
		novaSenhaRedefinir = null;
		confNovaSenhaRedefinir = null;
        
    }
	
	public void alterarSenha() {

		try {
			
			if (StringUtils.isBlank(senhaAtualRedefinir) || StringUtils.isBlank(novaSenhaRedefinir) || StringUtils.isBlank(confNovaSenhaRedefinir)) {
				
				UtilMensagens.mensagemWarn("Por favor, informe todos os campos para prosseguir!");
				return;
				
			}
			
			Optional<Usuario> usuarioDados = getDadosUsuarioLogado();
			
			boolean senhaEConfirmarNaoConferem = !novaSenhaRedefinir.contentEquals(confNovaSenhaRedefinir);
			boolean senhaAtualNaoCorresponde = !BCrypt.checkpw(senhaAtualRedefinir, usuarioDados.get().getSenha());
			boolean senhaInformadaIgualSenhaAtual = BCrypt.checkpw(novaSenhaRedefinir, usuarioDados.get().getSenha());
			boolean senhaNaoEForte = !passwordValidator.isSenhaForte(novaSenhaRedefinir);
			
			if (senhaEConfirmarNaoConferem) {

				UtilMensagens.mensagemWarn("Os Campos Senha e Confirmar Senha não Correspondem!");
				confNovaSenhaRedefinir = null;
				return;
				
			}			
				
			if (senhaAtualNaoCorresponde) {					

				UtilMensagens.mensagemAposRequest("A Senha Atual Informada não Corresponde!", FacesMessage.SEVERITY_WARN);
				senhaAtualRedefinir = null;
				return;
				
			} 
			
			if (senhaInformadaIgualSenhaAtual) {					

				UtilMensagens.mensagemAposRequest("A Nova Senha Deve Ser Diferente da Atual!", FacesMessage.SEVERITY_WARN);
				
				novaSenhaRedefinir = null;
				confNovaSenhaRedefinir = null;
				
				return;
				
			} 
			
			if (senhaNaoEForte) {					

				UtilMensagens.mensagemAposRequest("A senha informada não preeenche os requisitos mínimos de segurança!", FacesMessage.SEVERITY_WARN);
				
				novaSenhaRedefinir = null;
				confNovaSenhaRedefinir = null;			
				
				return;
				
			}			
			
			Usuario usuario = new Usuario();
			usuario.setId(UtilUserDetails.getUsuarioLogado().getId());
			usuario.setSenha(BCrypt.hashpw(confNovaSenhaRedefinir, BCrypt.gensalt()));
			
			if (usuarioService.alterarSenha(usuario) > 0) {
				
				UtilMensagens.mensagemAposRequest("Senha Alterada com Sucesso!", FacesMessage.SEVERITY_WARN);
				PrimeFaces.current().executeScript("PF('rdfpassword').hide()");
							
						
				
				return;				
				
			}	
			
		} catch (Exception e) {

			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao Realizar Uma ou Mais Validações!");
		
		} finally {
			
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
	
	public Optional<Usuario> getDadosUsuarioLogado() {

		return usuarioService.porId(UtilUserDetails.getUsuarioLogado().getId());

	}
	
	public void limparParametros() {
		
		senhaAtualRedefinir = null;
		novaSenhaRedefinir = null;
		confNovaSenhaRedefinir = null;
		
	}
				
}