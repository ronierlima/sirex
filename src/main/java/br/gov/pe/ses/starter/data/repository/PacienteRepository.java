package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	List<Paciente> findAll(Specification<Paciente> spec);

	Page<Paciente> findAll(Specification<Paciente> spec, Pageable page);
	
	

}
