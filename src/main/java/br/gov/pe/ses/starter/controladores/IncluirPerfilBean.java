package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.PermissaoDTO;
import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.FuncionalidadeService;
import br.gov.pe.ses.starter.service.interfaces.PerfilService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class IncluirPerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Funcionalidade> principais;

	private Set<Funcionalidade> todas = new HashSet<>();

	private List<Funcionalidade> selecionadas = new ArrayList<>();

	private List<PermissaoDTO> permissoes = new ArrayList<>();

	private Perfil perfil = new Perfil();;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private FuncionalidadeService funcionalidadeService;

	public boolean marcado;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	public IncluirPerfilBean() {

	}

	@PostConstruct
	public void inicializar() {
		permissoes = new ArrayList<>();
		buscarPerfil();
		criarPermissoes();
	}

	private void criarPermissoes() {
		principais = funcionalidadeService.buscarPrincipais();
		principais.forEach(funcionalidade -> {

			PermissaoDTO permissaoDTO = new PermissaoDTO(funcionalidade);

			if (perfil.getFuncionalidades() != null) {

				int totalVinculadas = perfil.getFilhas(funcionalidade).size();

				if (totalVinculadas > 0) {
					permissaoDTO.setMarcado(true);
				}
			}

			permissoes.add(permissaoDTO);
		});
	}

	private void buscarPerfil() {
		try {
			perfil = (Perfil) utilSessionBean.getParametro("perfilSelecionado");
			perfil = perfilService.porIdComDependencia(perfil.getId());
			perfil.getFuncionalidades().removeIf(f -> f.getFuncionalidadePai().getId().equals(f.getId()));
			selecionadas.addAll(perfil.getFuncionalidades());
			todas.addAll(selecionadas);
		} catch (Exception e) {
			perfil = new Perfil();
		}
	}

	public void selecionarTodas(PermissaoDTO permissao) {
		if (permissao.isMarcado()) {
			selecionadas.addAll(permissao.getFilhas());
			todas.addAll(permissao.getFilhas());
		} else {
			selecionadas.removeAll(permissao.getFilhas());
			todas.removeAll(permissao.getFilhas());
		}
	}

	public void aoSelecionarItem(AjaxBehaviorEvent event) {
		Long idPai = (Long) event.getComponent().getAttributes().get("idPai");
		todas.removeIf(p -> p.getFuncionalidadePai().getId().equals(idPai));
		checarFuncionalidadePai();
		todas.addAll(selecionadas);
	}

	private void checarFuncionalidadePai() {
		permissoes.forEach(permissaoDTO -> {

			List<Funcionalidade> vinculadas = buscarFilhas(permissaoDTO.getPai());

			if (vinculadas.isEmpty()) {
				permissaoDTO.setMarcado(false);
				return;
			}

			permissaoDTO.setMarcado(true);
		});
	}

	private List<Funcionalidade> buscarFilhas(Funcionalidade pai) {
		return selecionadas.stream().filter(f -> f.getFuncionalidadePai().equals(pai)).toList();
	}

	public void salvar() throws NegocioException {
		
		try {		
		
		perfil.setSelecionadas(todas);
		perfil = perfilService.cadastrar(perfil);
		UtilMensagens.msgInfoAposRequest("Operação realizada com sucesso.");
		FacesUtil.redirect("/paginas/perfil/listarPerfis.xhtml");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
