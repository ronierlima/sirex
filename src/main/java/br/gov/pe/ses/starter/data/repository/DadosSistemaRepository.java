package br.gov.pe.ses.starter.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.DadosSistema;

@Repository
public interface DadosSistemaRepository extends JpaRepository<DadosSistema, Long> {

	DadosSistema findTopByOrderByDataInclusaoDesc();

}
