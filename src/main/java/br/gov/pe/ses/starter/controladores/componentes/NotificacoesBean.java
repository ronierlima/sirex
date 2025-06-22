package br.gov.pe.ses.starter.controladores.componentes;

import br.gov.pe.ses.starter.mensageria.dto.MsgResponse;
import br.gov.pe.ses.starter.util.SistemaConst;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@ApplicationScoped
public class NotificacoesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SimpMessagingTemplate template;

	public NotificacoesBean() {

	}

	public void notificar() {
		template.convertAndSend(SistemaConst.canalEndpoint, new MsgResponse("Msg Teste"));
	}


}