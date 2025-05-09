package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

	List<Hospital> findAll(Specification<Hospital> spec);

	Page<Hospital> findAll(Specification<Hospital> spec, Pageable page);
	
	@Query("from Hospital h where h.ativo is true order by h.nome")
	List<Hospital> findAllAtivos();

}
