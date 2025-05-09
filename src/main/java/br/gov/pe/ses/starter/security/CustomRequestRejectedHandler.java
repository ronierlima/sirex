package br.gov.pe.ses.starter.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomRequestRejectedHandler implements RequestRejectedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,RequestRejectedException requestRejectedException) throws IOException, ServletException {
		response.sendError(HttpStatus.NOT_FOUND.value());
		
	}

}
