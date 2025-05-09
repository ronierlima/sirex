package br.gov.pe.ses.starter.util;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SistemaConst {

	public static final String wsName = "/websocket";

	public static final String wsRoot = "/app";
	
	public static final String user = "/user";

	public static final String wsEndpoint = "/notificar";

	public static final String canal = "/estoque";

	public static final String canalEndpoint = canal + wsEndpoint;
	
	public static final String canalPrivado = "/queue";
	
	public static final String chatEndpoint = canalPrivado+"/chat";

	public static final Integer LIMITE_RESPONSAVEIS_ATIVOS = 2;

	public static final Integer BOLSA = 1;

	public static final Integer ADJUVANTE = 2;

	public static final Integer RECORRENCIA_MENSAL = 2;

	public static final Integer COMORBIDADE_OUTRAS = 9;

	public static final Integer DOENCA_ONCOLOGICA = 5;

	public static final Integer NUMERO_NOTIFICACOES_LIMITE = 5;
	
	public static final Integer RECORENCIA_TEMPORAL_PADRAO_MESES_RETIRADA_BOLSA = 1;

	public static final String caminhoRelatorios = "classpath:relatorios/";;

	public static String caminhoUpload() {
		return Paths.get(System.getProperty("catalina.base"), "uploadEstomia").toString();
	}
	
	public static final String urlDominioSistema = "https://ostomizados.saude.pe.gov.br";

	public static final Integer RECORRENCIA_UNICA = 1;
	
	public static List<String> sugestoesHospitaisOrigem = Arrays.asList(
			"Centro Hospitalar de Portugal",
			"Complexo Hospitalar Unimed Recife - CHUR",
			"Ginecologia Hospital Bandeirantes",
			"Hospital Agamenon Magalhães - HAM",
			"Hospital Albert Sabin",
			"Hospital Alemão Oswaldo Cruz",
			"Hospital Barão de Lucena - HBL",
			"Hospital Belo Horizonte - HBH",
			"Hospital da Aeronáutica",
			"Hospital da Mulher",
			"Hospital da Polícia Militar",
			"Hospital das Clínicas - HC",
			"Hospital D'Ávila (Portugal)",
			"Hospital de Câncer de Pernambuco - HCP",
			"Hospital de Niterói",
			"Hospital do Capibaribe",
			"Hospital do IMIP - IMIP",
			"Hospital do Restauração - HR",
			"Hospital Dom Helder Câmara - HDHC",
			"Hospital dos Servidores do Estado - HSE",
			"Hospital Eduardo Campos - HEC",
			"Hospital Esperança",
			"Hospital Estadual Mário Covas",
			"Hospital Getúlio Vargas - HGV",
			"Hospital Infantil Maria Lucinda - HIML",
			"Hospital Jayme da Fonte - HJF",
			"Hospital Memorial Caruaru - HMC",
			"Hospital Memorial Jaboatão - HMJ",
			"Hospital Memorial São José - HMSJ",
			"Hospital Mestre Vitalino - HMV",
			"Hospital Metropolitano do Sul Dom Helder Câmara",
			"Hospital Miguel Arraes - HMA",
			"Hospital Naval do Recife - HNR",
			"Hospital Nossa Senhora das Graças - HNSG",
			"Hospital Nove de Julho",
			"Hospital Oscar Coutinho",
			"Hospital Regional de Palmares",
			"Hospital Regional Dom Moura em Garanhuns",
			"Hospital Regional Fernando Bezerra",
			"Hospital Santa Joana",
			"Hospital Santa Marcelina",
			"Hospital Santo Amaro",
			"Hospital São Camilo",
			"Hospital São Luiz - HSL",
			"Hospital São Marcos - HSM",
			"Hospital São Mateus - HSM",
			"Hospital Unimed - HU",
			"Hospital Universitário Oswaldo Cruz",
			"Hospital Universitário Oswaldo Cruz - HUOC",
			"Hospital Vale do Una",
			"Hospital Vasco Lucena - HVL",
			"Instituto de Medicina Integral Professor Fernando Figueira - IMIP",
			"Instituto do Câncer de São Paulo",
			"Real Hospital Português - RHP");

	
}
