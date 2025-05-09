package br.gov.pe.ses.starter.util.viacep;

import java.io.BufferedReader;
import java.io.IOException;

public class UtilViaCEP {

	public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
		String resposta, jsonString = "";
		while ((resposta = buffereReader.readLine()) != null) {
			jsonString += resposta;
		}
		return jsonString;
	}

}
