package br.gov.pe.ses.starter.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@Component
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator<String> {
	
	public boolean isSenhaForte(String senha) {
		
		if (StringUtils.isEmpty(senha)) {
			
			return false;
			
		} else {
			
			/*A senha deve conter pelo menos 8 caracteres e incluir letras e números.*/
			return senha.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");
			
			
		}		
	}

	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		
		if (!this.isSenhaForte((String) value)) {
			
			String msg = "A Senha Informada não Atende os Requisitos Mínimos de Segurança!";
			
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));			
			
		}
		
	}
	   
}
