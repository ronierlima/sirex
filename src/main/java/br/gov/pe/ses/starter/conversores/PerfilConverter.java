package br.gov.pe.ses.starter.conversores;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.pe.ses.starter.data.repository.PerfilRepository;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Perfil.class, managed = true)
public class PerfilConverter implements Converter<Perfil> {

	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public Perfil getAsObject(FacesContext context, UIComponent component, String value) {
		Perfil retorno = null;
		if (StringUtils.isNotEmpty(value)) {
			Long id = Long.parseLong(value);
			retorno = perfilRepository.porIdComDependencias(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Perfil value) {
		if (value != null) {
			return ((Perfil) value).getId().toString();
		}

		return "";
	}

}