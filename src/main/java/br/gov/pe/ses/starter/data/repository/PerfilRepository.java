package br.gov.pe.ses.starter.data.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Unidade;
import br.gov.pe.ses.starter.entidades.publico.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	@Query("SELECT p FROM Perfil p left JOIN FETCH p.funcionalidades WHERE p.id = :id")
	Perfil porIdComDependencias(@Param("id") Long id);

	List<Perfil> findAll(Specification<Perfil> spec);

	Page<Perfil> findAll(Specification<Perfil> spec, Pageable page);

	@Query("from Perfil p where p.unidade=:unidade and p.ativo is true")
	List<Perfil> findAllAtivos(Unidade unidade);

}
