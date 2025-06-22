package br.gov.pe.ses.starter.controladores.componentes;

import java.io.Serializable;

import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@Data
@SessionScoped
public class TopBarMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer totalGeralNotificacoes = 0;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UsuarioService usuarioService;


	@Autowired
	private UtilSessionBean utilSessionBean;

	public TopBarMBean() {

	}

	@PostConstruct
	public void inicializar() {
		UtilMensagens.addInfoMessageGrowl("Atencao", "Msg Disparada Para Todos");
	}

}