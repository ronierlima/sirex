package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;

@Repository
public interface MacroRepository extends JpaRepository<MacroRegiao, Long> {

	@Query("from MacroRegiao m ORDER BY m.nome")
	List<MacroRegiao> listar();

}
