package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

	@Query("from Municipio m ORDER BY m.nome")
	List<Municipio> listarTodos();

	@Query("from Municipio m WHERE m.gere=:gere ORDER BY m.nome")
	List<Municipio> listarPorGere(Gere gere);

}
