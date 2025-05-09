package br.gov.pe.ses.starter.util.viacep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pe.ses.starter.dto.ViaCep;

public class IntegracaoApiViaCep {

	@SuppressWarnings("deprecation")
	public static ViaCep buscaCep(String cep) throws Exception {

		try {

			String enderecoURL = "https://viacep.com.br/ws/" + cep + "/json/";
			
			URL url = new URL(enderecoURL);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			conexao.setRequestMethod("GET");
			conexao.setDoInput(true);
			BufferedReader buff = new BufferedReader(new InputStreamReader((conexao.getInputStream()), "utf-8"));

			String convertJsonString = UtilViaCEP.converteJsonEmString(buff);
			ObjectMapper objectMapper = new ObjectMapper();
			ViaCep endereco = objectMapper.readValue(convertJsonString, ViaCep.class);
			return endereco;

		} catch (Exception msgErro) {
			throw new Exception("Erro ao Conectar na API: " + msgErro.toString());
		}

	}

}
