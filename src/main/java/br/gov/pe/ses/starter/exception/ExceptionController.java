package br.gov.pe.ses.starter.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ExceptionController implements ErrorController {
	
	@GetMapping("/error")
	public String handleError(HttpServletRequest request) {		
		
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {	        	
	            return "/notfound.xhtml";
	        }
	        
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "/error.xhtml";
	        }
	        
	    }
	    
	    return "/error.xhtml";
	}

	public String getErrorPath() {
		return null;
	}

}


