package br.gov.pe.ses.starter.service.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.UsuarioFiltroDTO;
import br.gov.pe.ses.starter.dto.UsuarioSimplesDTO;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface UsuarioService {
	
	public Optional<Usuario> login(String username);
	
	public Page<Usuario> buscaPaginada(UsuarioFiltroDTO filtro);

	public Usuario alterarStatus(Usuario usuario);

	public Usuario cadastrar(Usuario usuario) throws NegocioException, Exception;

	public Usuario porIdComDependencia(Long id);
	
	public Optional<Usuario> porId(Long id);

	public Usuario porCpf(String cpf);

	public Optional<Usuario> porEmail(String email) throws NegocioException;
	
	public int resetToken(Usuario u);
	
	public Optional<Usuario> usuarioPorResetToken(String resetToken) throws NegocioException;

	int alterarSenha(Usuario u) throws NegocioException;

	public List<UsuarioSimplesDTO> usuariosSimplesPorHospital(Hospital hospital) throws NegocioException;

	public int alterarHospitalPadrao(Usuario u) throws NegocioException;

	public Set<Usuario> usuariosCompletosPorHospital(Hospital hospital) throws NegocioException;

}
