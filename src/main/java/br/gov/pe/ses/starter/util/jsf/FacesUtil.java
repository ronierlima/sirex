package br.gov.pe.ses.starter.util.jsf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.security.UsuarioSistema;
import jakarta.faces.context.FacesContext;

public class FacesUtil {

	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	public static boolean isNotPostback() {
		return !isPostback();
	}

	public static Long getIdUsuarioLogado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario.getUsuario().getId();
	}

	public static Hospital getHospitalSelecionado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario.getUsuario().getHospital();
	}

	/**
	 * Direciona para outra pagina mudando a url
	 */
	public static void redirect(String uri) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Usuario getUsuarioLogado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario.getUsuario();
	}

	public static void validationFailed() {
		FacesContext.getCurrentInstance().validationFailed();
	}

	public static String getSessionId() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
	}

	public static Map<String, Object> getConfigDlg() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("closable", true);
		opcoes.put("widgetvar", "wdgPesquisarPacientes");
		opcoes.put("closeOnEscape", true);
		opcoes.put("resizable", false);
		opcoes.put("responsive", true);
		opcoes.put("width", "70%");
		opcoes.put("height", "60%");
		opcoes.put("contentWidth", "100%");
		opcoes.put("contentHeight", "100%");
		opcoes.put("showHeader", false);
		opcoes.put("position", "top");

		return opcoes;
	}

}