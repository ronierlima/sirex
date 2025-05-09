package br.gov.pe.ses.starter.util.jsf;

import java.io.IOException;

import org.primefaces.PrimeFaces;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UtilMensagens {

	public static void mensagemAposRequest(String mensagem, Severity severity) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage(null, new FacesMessage(severity, mensagem, ""));
	}
	
	public static void msgInfoAposRequest(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));
	}
	
	public static void msgInfoAposRequest(String mensagem, String detalhe) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, detalhe, detalhe));
	}

	public static void mensagemError(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}

	public static void mensagemFatal(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagem, ""));
	}

	public static void mensagemInfo(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));
	}

	public static void mensagemWarn(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, ""));
	}

	public static void addInfoMessageGrowl(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, message));
	}

	public static void addWarnMessageGrowl(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, message));
	}

	public static void addErrorMessageGrowl(String titulo, String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, message));
	}

	public static void adicionarMsgDelete() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação Concluída", "Registro Excluído com sucesso!"));
	}

	public static void adicionarMsgError(String msgError) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, "Error!"));
	}

	public static void adicionarMsgInfo(String msgInfo) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, msgInfo, "Informação"));
	}

	public static void adicionarMsgSave() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação Concluída", "Registro Salvo com sucesso!"));
	}

	public static void adicionarMsgUpdate() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação Concluída", "Registro Atualizado com sucesso!"));
	}
	
	public static void mensagemComponenteEspecifico(String componenteId, String mensagem, Severity severity) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(componenteId, new FacesMessage(severity, mensagem, ""));
	}

	public static String getBrowser() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("User-Agent");
	}

	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	private static String getContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static Object getFlash(String objeto) {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(objeto);
	}

	public static String getPaginaAtual() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public static String getParam(String param) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param);
	}

	public static String getRealPah(String path) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	public static void msgInDialog(String summary, String detail) {
		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(summary, detail));
	}

	public static Object putFlash(String alias, Object objeto) {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash().put(alias, objeto);
	}

	public static String putParam(String name, String param) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put(name, param);
	}

	public static void redirect(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(getContextPath() + url);
	}

}
