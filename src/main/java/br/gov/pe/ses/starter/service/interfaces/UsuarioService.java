package br.gov.pe.ses.starter.service.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.gov.pe.ses.starter.dto.UsuarioFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.exception.NegocioException;

public interface UsuarioService {

	public Optional<Usuario> login(String username);

	public Page<Usuario> buscaPaginada(UsuarioFiltroDTO filtro);

	public Usuario alterarStatus(Usuario usuario);

	public Usuario cadastrar(Usuario usuario) throws NegocioException, Exception;

	public Usuario porIdComDependencia(Long id);

	public Usuario porEmail(String email) throws NegocioException;

	public int resetToken(Usuario u);

	public Usuario usuarioPorResetToken(String resetToken) throws NegocioException;

	int alterarSenha(Usuario u) throws NegocioException;


	public int alterarHospitalPadrao(Usuario u) throws NegocioException;



}
