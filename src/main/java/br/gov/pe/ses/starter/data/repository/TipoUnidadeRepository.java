package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;

@Repository
public interface TipoUnidadeRepository extends JpaRepository<TipoUnidade, Long> {

	List<TipoUnidade> findAll(Specification<TipoUnidade> spec);

	Page<TipoUnidade> findAll(Specification<TipoUnidade> spec, Pageable page);

	@Query("FROM TipoUnidade  WHERE ativo is true ORDER BY descricao")
	List<TipoUnidade> findAllAtivos();

}
