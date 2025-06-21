package br.gov.pe.ses.starter.controladores.componentes;

import static br.gov.pe.ses.starter.util.SistemaConst.caminhoRelatorios;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.security.UtilUserDetails;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

@Component
public class GerarRelatorio implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int BLOCK_SIZE = 40960;

	private static final int MIN_GROW_COUNT = 100;

	private static final int MAX_SIZE = 100;

	private static final String FOLDER_TMP = File.separator + "tmp";

	@Autowired
	private ResourceLoader resourceLoader;

	public GerarRelatorio() {

	}

	@SuppressWarnings("rawtypes")
	public byte[] gerar(String relatorio, HashMap<String, Object> parametros, List lista) throws NegocioException {

		JRAbstractLRUVirtualizer fileVirtualizer = null;
		try {

			criarDiretorio();

			JRSwapFile arquivoSwap = new JRSwapFile(FOLDER_TMP, BLOCK_SIZE, MIN_GROW_COUNT);
			fileVirtualizer = new JRSwapFileVirtualizer(MAX_SIZE, arquivoSwap, true);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

			String caminhoRelatorioJrxml = caminhoRelatorios + relatorio + ".jrxml";
			String pathLogos = caminhoRelatorios + "logos/";

			Resource recursoJrxml = resourceLoader.getResource(caminhoRelatorioJrxml);
			Resource pastaPrincipal = resourceLoader.getResource(caminhoRelatorios);
			Resource pastaLogos = resourceLoader.getResource(pathLogos);

			InputStream inputStream = recursoJrxml.getInputStream();
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

			parametros.put(JRParameter.REPORT_VIRTUALIZER, fileVirtualizer);
			parametros.put("SUBREPORT_DIR", pastaPrincipal.getURL().toString());
			parametros.put("MATRICULA", UtilUserDetails.getUsuarioLogado().getLogin());
			parametros.put("IMPRESSO_POR", UtilUserDetails.getUsuarioLogado().getPessoa().getNome());
			parametros.put("LOGO_PATH", pastaLogos.getURL().toString());
			parametros.put("REPORT_LOCALE", Locale.of("pt", "BR"));
			parametros.put("HOSPITAL", UtilUserDetails.getUsuarioLogado().getUnidade().getNome());

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			NegocioException negocioException = new NegocioException("Erro ao gerar relatório." + e.getMessage());
			negocioException.setExibeDialogo(true);
			throw negocioException;
		} finally {
			if (fileVirtualizer != null) {
				fileVirtualizer.cleanup();
			}
		}

	}

	private void criarDiretorio() {

		try {

			File diretorio = new File(FOLDER_TMP);

			if (!diretorio.exists()) {
				diretorio.mkdir();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}