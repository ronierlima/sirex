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
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("SELECT u from Usuario u JOIN FETCH u.hospital h WHERE upper(u.login) = upper(:login) ")
	Optional<Usuario> login(@Param("login") String login);

	List<Usuario> findAll(Specification<Usuario> spec);

	Page<Usuario> findAll(Specification<Usuario> spec, Pageable page);

	@Query("select u from Usuario u left join fetch u.perfis left join fetch u.hospital h where u.id = :id")
	Usuario porIdComDependencias(@Param("id") Long id);

	@Query("select u from Usuario u where u.id = :id")
	Optional<Usuario> porId(@Param("id") Long id);

	@Query("select u from Usuario u where u.cpf = :cpf")
	Usuario porCpf(@Param("cpf") String cpf);

	@Query("select u from Usuario u where lower(u.email) = lower(:email)")
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

	@Query("SELECT new br.gov.pe.ses.starter.dto.UsuarioSimplesDTO(u.id,u.nome) FROM Usuario u where u.hospital = :hospital order by u.nome")
	List<UsuarioSimplesDTO> usuariosSimplesPorHospital(Hospital hospital);

	@Modifying
	@Transactional
	@Query("update Usuario u set u.hospital=:#{#usuario.hospital} where u.id=:#{#usuario.id}")
	int atualizarHospitalPadrao(Usuario usuario);

	@Query("SELECT u FROM Usuario u where u.hospital = :hospital and u.ativo is true order by u.nome")
	Set<Usuario> usuariosCompletosAtivosPorHospital(Hospital hospital);

}
