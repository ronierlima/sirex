package br.gov.pe.ses.starter.controladores.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.security.Seguranca;
import br.gov.pe.ses.starter.security.UtilUserDetails;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;

@Component
@SessionScoped
public class UtilSessionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object objeto;

	private Map<String, Object> parametros = new HashMap<>();

	private StreamedContent imagemUsuario;

	private List<Hospital> hospitaisVinculadosUsuario = new ArrayList<Hospital>();

	@Autowired
	private Seguranca seguranca;

	@Lazy
	@Autowired
	private TopBarMBean topBarMBean;

	@Autowired
	private UsuarioService usuarioService;

	public UtilSessionBean() {

	}

	public void addParametro(String nome, Object valor) {
		parametros.put(UtilUserDetails.getUsuarioLogado().getId() + "_" + nome, valor);

	}

	public Object getParametro(String nome) {
		objeto = parametros.get(UtilUserDetails.getUsuarioLogado().getId() + "_" + nome);
		return objeto;
	}

	@PreDestroy
	public void limparParametros() {
		parametros.clear();
	}

	@PostConstruct
	public void inicializar() {
		imagemUsuario = getImagemIniciaisUsuario();
		listarHospitaisVinculadosUsuario();
	}

	public StreamedContent getImagemIniciaisUsuario() {

		try {

			String nomeCompleto = UtilUserDetails.getUsuarioLogado().getNome();
			String iniciais = getIniciais(nomeCompleto);

			int width = 100, height = 100;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.BLUE);
			g2d.fillOval(0, 0, width, height);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 40));
			FontMetrics fm = g2d.getFontMetrics();
			int x = (width - fm.stringWidth(iniciais)) / 2;
			int y = (height - fm.getHeight()) / 2 + fm.getAscent();

			g2d.drawString(iniciais, x, y);
			g2d.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(baos.toByteArray()))
					.contentType("image/png").build();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getIniciais(String nomeCompleto) {

		if (!StringUtils.isAllEmpty(nomeCompleto)) {

			String[] parts = nomeCompleto.trim().split("\\s+");
			if (parts.length >= 2) {
				return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
			} else if (parts.length == 1) {
				return parts[0].substring(0, 1).toUpperCase();
			}
			return "";
		}

		return "NI";

	}

	public StreamedContent getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(StreamedContent imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
	}

	public List<Hospital> getHospitaisVinculadosUsuario() {
		return hospitaisVinculadosUsuario;
	}

	public void setHospitaisVinculadosUsuario(List<Hospital> hospitaisVinculadosUsuario) {
		this.hospitaisVinculadosUsuario = hospitaisVinculadosUsuario;
	}

	protected void listarHospitaisVinculadosUsuario() {

		hospitaisVinculadosUsuario = new ArrayList<Hospital>();

		try {

			hospitaisVinculadosUsuario = seguranca.getUsuarioLogado().getUsuario().getHospitaisAssociados();

		} catch (Exception e) {

			UtilMensagens.mensagemError("Erro ao buscar os hospitais vinculados ao usuário!");
			e.printStackTrace();

		}

	}

	public void onHospitalChange() {

		try {

			seguranca.atualizarPermissoes();
			limparParametros();

			usuarioService.alterarHospitalPadrao(FacesUtil.getUsuarioLogado());

			StringBuilder texto = new StringBuilder();

			texto.append("As permissões foram atualizadas conforme o contexto do hospital: ")
					.append(FacesUtil.getHospitalSelecionado().getSigla())
					.append(". Certas funcionalidades podem não estar disponíveis.");

			UtilMensagens.msgInfoAposRequest(texto.toString());
			FacesUtil.redirect("/paginas/principal.xhtml?faces-redirect=true");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isRenderizaSeletorHospitais() {

		return !hospitaisVinculadosUsuario.isEmpty() && hospitaisVinculadosUsuario.size() > 1;

	}

}
