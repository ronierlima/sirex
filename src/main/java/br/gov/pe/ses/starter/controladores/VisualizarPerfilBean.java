package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.dto.PermissaoDTO;
import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.service.interfaces.FuncionalidadeService;
import br.gov.pe.ses.starter.service.interfaces.PerfilService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class VisualizarPerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Funcionalidade> principais;

	private List<Funcionalidade> selecionadas = new ArrayList<>();

	private List<PermissaoDTO> permissoes = new ArrayList<>();

	private Perfil perfil = new Perfil();;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private FuncionalidadeService funcionalidadeService;
	
	@Autowired
	private UtilSessionBean utilSessionBean;

	public boolean marcado;

	public VisualizarPerfilBean() {

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
		} catch (Exception e) {
			perfil = new Perfil();
		}
	}

}
