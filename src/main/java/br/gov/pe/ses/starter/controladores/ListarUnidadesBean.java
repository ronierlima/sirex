package br.gov.pe.ses.starter.controladores;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.lazy.UnidadeLazyDataModel;
import br.gov.pe.ses.starter.service.interfaces.GereService;
import br.gov.pe.ses.starter.service.interfaces.MunicipioService;
import br.gov.pe.ses.starter.service.interfaces.TipoUnidadeService;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
@Data
public class ListarUnidadesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private UnidadeFiltroDTO filtro;

    private Unidade unidadeSelecionada;

    private List<TipoUnidade> tipos;
    private List<Gere> geres;
    private List<Municipio> municipios;

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private TipoUnidadeService tipoUnidadeService;

    @Autowired
    private GereService gereService;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private UnidadeLazyDataModel model;

    @Autowired
    private UtilSessionBean utilSessionBean;


    @PostConstruct
    public void inicializar() {
        unidadeSelecionada = new Unidade();
        filtro = new UnidadeFiltroDTO();

        iniciarListas();
        pesquisar();
    }

    private void iniciarListas() {
        tipos = tipoUnidadeService.listarAtivos();
        geres = gereService.listarTodos();
        municipios = municipioService.listarTodos();
    }

    public void pesquisar() {
        model.setFiltro(filtro);
    }

    public void alterarStatus() throws NegocioException {
        unidadeService.alterarStatus(unidadeSelecionada);
        inicializar();
    }

    public String alterar() {
        utilSessionBean.addParametro("unidadeSelecionada", unidadeSelecionada);
        return "/paginas/unidade/incluirUnidade.xhtml?faces-redirect=true";
    }

    public String visualizar() {
        utilSessionBean.addParametro("unidadeSelecionada", unidadeSelecionada);
        return "/paginas/unidade/visualizarUnidade.xhtml?faces-redirect=true";
    }

    public String incluirUnidade() {
        utilSessionBean.limparParametros();
        return "/paginas/unidade/incluirUnidade.xhtml?faces-redirect=true";
    }

}
