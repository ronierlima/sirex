package br.gov.pe.ses.starter.mensageria.resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import br.gov.pe.ses.starter.mensageria.dto.Msg;
import br.gov.pe.ses.starter.mensageria.dto.MsgResponse;
import br.gov.pe.ses.starter.util.SistemaConst;

@Controller
public class MsgController {

	@MessageMapping(SistemaConst.wsEndpoint)
	@SendTo(SistemaConst.canalEndpoint)
	public MsgResponse getUser(Msg user) {

		return new MsgResponse("Hi " + user.getName());
	}
}
