package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.TipoUnidadeService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
@ViewScoped
@Data
public class IncluirTiposBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TipoUnidadeService tipoUnidadeService;

	private TipoUnidade tipo;

	@PostConstruct
	public void inicializar() {
		tipo = new TipoUnidade();
	}

	public void salvar() throws NegocioException {
		tipoUnidadeService.cadastrar(tipo);
		UtilMensagens.msgInfoAposRequest("Sucesso", "Operacao Realizada");
		FacesUtil.redirect("/paginas/tipos/listarTipos.xhtml");
	}

}
