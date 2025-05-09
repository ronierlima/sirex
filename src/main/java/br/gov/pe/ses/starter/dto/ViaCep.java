package br.gov.pe.ses.starter.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViaCep {

	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String localidade;

	private String uf;

	private String ibge;

	private String gia;

	private String ddd;

	private String siafi;

	private String unidade;

	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
