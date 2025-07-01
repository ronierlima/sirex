package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.List;

import br.gov.pe.ses.starter.service.interfaces.GereService;
import br.gov.pe.ses.starter.service.interfaces.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;
import br.gov.pe.ses.starter.entidades.publico.Municipio;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import br.gov.pe.ses.starter.service.interfaces.TipoUnidadeService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class IncluirUnidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Unidade unidade;

    private List<TipoUnidade> tipos;

    @Autowired
    private TipoUnidadeService tipoUnidadeService;

    @Autowired
    private GereService gereService;

    @Autowired
    private MunicipioService municipioService;

    public List<MacroRegiao> macros;

    public List<Gere> geres;

    public List<Municipio> municipios;

    @Autowired
    private UnidadeService hospitalService;

    @Autowired
    private UtilSessionBean utilSessionBean;

    @PostConstruct
    public void inicializar() {
        unidade = new Unidade();
        buscarUnidade();
    }

    private void buscarUnidade() {
        try {
            unidade = (Unidade) utilSessionBean.getParametro("unidadeSelecionada");
            unidade = hospitalService.porIdComDependencias(unidade.getId());
        } catch (Exception e) {
            unidade = new Unidade();
        }

        macros = hospitalService.listarMacros();

        if (unidade.isExistente()) {
            aoSelecionarMacro();
            aoSelecionarGere();
        }

        tipos = tipoUnidadeService.listarAtivos();
    }

    public void aoSelecionarMacro() {
        MacroRegiao macroSelecionada = unidade.getMunicipio().getGere().getMacroRegiao();
        geres = gereService.listarPorMacro(macroSelecionada);
    }

    public void aoSelecionarGere() {
        Gere gereSelecionada = unidade.getMunicipio().getGere();
        municipios = municipioService.listarPorGere(gereSelecionada);
    }

    public void cadastrar() throws NegocioException {

        hospitalService.cadastrar(unidade);
        inicializar();
        UtilMensagens.msgInfoAposRequest("Unidade Cadastrada");
        FacesUtil.redirect("/paginas/unidade/listarUnidades.xhtml?faces-redirect=true");
    }

}
