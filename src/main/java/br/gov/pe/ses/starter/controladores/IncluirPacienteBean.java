package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pe.ses.starter.entidades.publico.Paciente;
import br.gov.pe.ses.starter.exception.NegocioException;
import br.gov.pe.ses.starter.service.interfaces.PacienteService;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import br.gov.pe.ses.starter.util.jsf.UtilMensagens;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@ViewScoped
@Data
public class IncluirPacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PacienteService pacienteService;

	private Paciente paciente;

	@PostConstruct
	public void inicializar() {

		paciente = new Paciente();
		paciente.setUnidadeCadastro(FacesUtil.getUnidadeSelecionado());

		String param = FacesUtil.getParametro("paciente");
		if (param != null) {

			Long idPaciente = null;
			
			try {
				idPaciente = Long.parseLong(param);
			} catch (NumberFormatException e) {
				UtilMensagens.msgInfoAposRequest("Atencao", "Paciente Nao Encontrado");
				FacesUtil.redirect("/paginas/paciente/listarPacientes.xhtml");
			}

			paciente = pacienteService.porId(idPaciente);
		}

	}

	public void cadastrar() throws NegocioException {
		pacienteService.cadastrar(paciente);

		UtilMensagens.msgInfoAposRequest("Sucesso", "Paciente Cadastrado/Editado");
		FacesUtil.redirect("/paginas/paciente/listarPacientes.xhtml");
	}
}
