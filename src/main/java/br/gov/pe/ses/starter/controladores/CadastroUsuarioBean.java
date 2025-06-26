package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.controladores.componentes.UtilSessionBean;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.UnidadeService;
import br.gov.pe.ses.starter.service.interfaces.PerfilService;
import br.gov.pe.ses.starter.service.interfaces.UsuarioService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private List<Unidade> hospitais;
	private DualListModel<Unidade> dualListModelHospitais;
	private DualListModel<Perfil> dualListModelPerfis;
	List<Perfil> perfisDisponiveis;
	List<Unidade> hospitaisDisponiveis;
	private Unidade hospitalSelecionado;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UnidadeService hospitalService;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UtilSessionBean utilSessionBean;

	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
		hospitalSelecionado = new Unidade();
		usuario.setHospitaisAssociados(new ArrayList<Unidade>());
		usuario.setPerfis(new ArrayList<Perfil>());
		buscarUsuario();
		listarPerfisDisponiveis();
		listarHospitaisDisponiveis();
		dualListModelPerfis = new DualListModel<Perfil>(perfisDisponiveis, usuario.getPerfis());
		dualListModelHospitais = new DualListModel<Unidade>(hospitaisDisponiveis, usuario.getHospitaisAssociados());
		getListaHospitaisAtivos();

	}

	@SuppressWarnings("unchecked")
	public void onTransferPerfil(TransferEvent event) {
		List<Perfil> perfisTransferidos = (List<Perfil>) event.getItems();

		for (Perfil perfil : perfisTransferidos) {

			if (event.isAdd()) {
				if (usuario.getPerfis().contains(perfil) == false) {
					usuario.getPerfis().add(perfil);
				}
			}

			if (event.isRemove()) {
				usuario.getPerfis().remove(perfil);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void onTransferHospital(TransferEvent event) {

		try {

			List<Unidade> hospitaisMovimentados = (List<Unidade>) event.getItems();

			for (Unidade hospital : hospitaisMovimentados) {

				if (event.isAdd()) {

					if (usuario.getHospitaisAssociados().contains(hospital) == false) {
						usuario.getHospitaisAssociados().add(hospital);
						UtilMensagens.mensagemInfo("Hospital " + hospital.getSigla() + " adicionado com sucesso!");
					}
				}

				if (event.isRemove()) {
					usuario.getHospitaisAssociados().remove(hospital);
					UtilMensagens.mensagemInfo("Hospital " + hospital.getSigla() + " removido com sucesso!");
				}
			}

			UtilMensagens.mensagemWarn("Clique em salvar para gravar as informações!");
			getListaHospitaisAtivos();
			hospitalSelecionado = new Unidade();
			listarPerfisDisponiveis();

		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao transferir hospitais!");
		}

	}

	public void cadastrar() {

		try {

			if (usuario.getHospitaisAssociados().isEmpty()) {
				UtilMensagens.mensagemWarn("Selecione ao menos um hospital!");
				return;
			}

			if (usuario.getPerfis().isEmpty()) {
				UtilMensagens.mensagemWarn("Selecione ao menos um perfil!");
				return;
			}

			usuarioService.cadastrar(usuario);
			UtilMensagens.mensagemAposRequest("Usuário cadastrado/editado com sucesso!", FacesMessage.SEVERITY_INFO);
			FacesUtil.redirect("/paginas/usuario/listarUsuarios.xhtml");
			reinstanciarVariaveis();

		} catch (Exception e) {

			if (e.getMessage().contains("cpf_unique")) {
				UtilMensagens.mensagemWarn("O CPF informado já está cadastrado no banco de dados!");
			} else if (e.getMessage().contains("uk_usuario_email")) {
				UtilMensagens.mensagemWarn("O e-mail informado já está cadastrado no banco de dados!");
			} else if (e.getMessage().contains("uk_usuario_login")) {
				UtilMensagens.mensagemWarn("O login informado já está cadastrado no banco de dados!");
			} else if (e.getMessage().contains("usuario_hospital_unique")) {
				UtilMensagens.mensagemWarn("O hospital selecionado já está vinculado ao usuário!");
			} else {
				UtilMensagens.mensagemError(e.getMessage());
			}

			e.printStackTrace();

		}

	}

	public void getListaHospitaisAtivos() {

		hospitais = new ArrayList<Unidade>();

		try {

			if (dualListModelHospitais != null && dualListModelHospitais.getTarget() != null) {

				hospitais = dualListModelHospitais.getTarget();

			} else {

				hospitais = usuario.getHospitaisAssociados();

			}

		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemError("Erro ao listar hospitais do usuário!");
		}

	}

	protected void listarPerfisDisponiveis() {

		if (usuario.getHospitaisAssociados().isEmpty()) {
			usuario.setPerfis(new ArrayList<Perfil>());
		}

		if (hospitalSelecionado == null || hospitalSelecionado.getId() == null
				|| usuario.getHospitaisAssociados().isEmpty()) {
			perfisDisponiveis = new ArrayList<Perfil>();
			dualListModelPerfis = new DualListModel<Perfil>(perfisDisponiveis, usuario.getPerfis());
			return;
		}

		if (!usuario.isNovo()) {

			perfisDisponiveis = perfilService.listarPerfisAtivos(hospitalSelecionado);
			perfisDisponiveis.removeAll(usuario.getPerfis());

		} else {

			perfisDisponiveis = perfilService.listarPerfisAtivos(hospitalSelecionado);

		}

		dualListModelPerfis = new DualListModel<Perfil>(perfisDisponiveis, usuario.getPerfis());

	}

	protected void listarHospitaisDisponiveis() {

		hospitaisDisponiveis = new ArrayList<Unidade>();

		List<Unidade> hospitais = hospitalService.listarUnidadesAtivas();

		hospitais.stream().forEach(p -> {

			hospitaisDisponiveis.add(p);

		});

		if (!usuario.isNovo()) {

			hospitaisDisponiveis.removeAll(usuario.getHospitaisAssociados());

		}

	}

	public void reinstanciarVariaveis() {
		usuario = new Usuario();
		hospitalSelecionado = new Unidade();
		listarPerfisDisponiveis();
		listarHospitaisDisponiveis();
		dualListModelPerfis = new DualListModel<Perfil>(perfisDisponiveis, usuario.getPerfis());
		dualListModelHospitais = new DualListModel<Unidade>(hospitaisDisponiveis, usuario.getHospitaisAssociados());
		getListaHospitaisAtivos();
	}

	private void buscarUsuario() {
		try {
			usuario = (Usuario) utilSessionBean.getParametro("usuarioSelecionado");
			usuario = usuarioService.porIdComDependencia(usuario.getId());
		} catch (Exception e) {
			usuario = new Usuario();
		}
	}

	public void onHospitalChange() throws NegocioException {

		listarPerfisDisponiveis();
		dualListModelPerfis = new DualListModel<Perfil>(perfisDisponiveis, usuario.getPerfis());

	}

	public void setarHospitalPadraoDoUsuario() {

		if (hospitalSelecionado == null || hospitalSelecionado.getId() == null) {
			UtilMensagens.mensagemWarn("Selecione um hospital para definir o padrão!");
			return;
		}

		UtilMensagens.mensagemInfo("Hospital padrão definido, clique em salvar para gravar as informações!");
		usuario.setUnidade(hospitalSelecionado);

	}

}
