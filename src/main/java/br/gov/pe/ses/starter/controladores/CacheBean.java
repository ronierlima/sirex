package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.gov.pe.ses.starter.service.interfaces.CacheService;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.enterprise.context.SessionScoped;
import lombok.Data;

@SessionScoped
@Component
@Data
public class CacheBean implements Serializable {

	private static final long serialVersionUID = 1L;	

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UnidadeService unidadeService;

	public void testarCache() {		
		unidadeService.listarUnidadesAtivas();
	}
	
	public void limparCache() {
        cacheService.limparCache("unidadesAtivasCache");
        UtilMensagens.mensagemInfo("Cache limpo com sucesso!");
        
    }

}
