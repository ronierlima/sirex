package br.gov.pe.ses.starter.data.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pe.ses.starter.dto.UsuarioSimplesDTO;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("SELECT u from Usuario u JOIN FETCH u.unidade h WHERE upper(u.login) = upper(:login) ")
	Optional<Usuario> login(@Param("login") String login);

	List<Usuario> findAll(Specification<Usuario> spec);

	Page<Usuario> findAll(Specification<Usuario> spec, Pageable page);

	@Query("select u from Usuario u left join fetch u.perfis left join fetch u.unidade h where u.id = :id")
	Usuario porIdComDependencias(@Param("id") Long id);

	@Query("select u from Usuario u where u.id = :id")
	Optional<Usuario> porId(@Param("id") Long id);

	@Query("select u from Usuario u where u.pessoa.cpf = :cpf")
	Usuario porCpf(@Param("cpf") String cpf);

	@Query("select u from Usuario u where lower(u.pessoa.email) = lower(:email)")
	Optional<Usuario> porEmail(@Param("email") String email);

	@Modifying
	@Transactional
	@Query("update Usuario u set u.resetToken=:resetToken,u.dataHoraExpToken=:dataExpToken where u.id=:idUsuario")
	int definirResetToken(@Param("resetToken") String resetToken, @Param("dataExpToken") LocalDateTime datatExpToken,
			@Param("idUsuario") Long id);

	@Query("from Usuario u where u.resetToken=:resetToken")
	Optional<Usuario> usuarioPorResetToken(String resetToken);

	@Modifying
	@Transactional
	@Query("update Usuario u set u.senha=:#{#usuario.senha},u.dataHoraResetSenha=current_timestamp where u.id=:#{#usuario.id}")
	int atualizarSenha(Usuario usuario);

	@Query("SELECT new br.gov.pe.ses.starter.dto.UsuarioSimplesDTO(u.id,u.pessoa.nome) FROM Usuario u where u.unidade = :unidade order by u.pessoa.nome")
	List<UsuarioSimplesDTO> usuariosSimplesPorHospital(Unidade unidade);

	@Modifying
	@Transactional
	@Query("update Usuario u set u.unidade=:#{#usuario.unidade} where u.id=:#{#usuario.id}")
	int atualizarUnidadePadrao(Usuario usuario);

	@Query("SELECT u FROM Usuario u where u.unidade = :unidade and u.ativo is true order by u.pessoa.nome")
	Set<Usuario> usuariosCompletosAtivosPorHospital(Unidade unidade);

}
