package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Gere;
import br.gov.pe.ses.starter.entidades.publico.MacroRegiao;

@Repository
public interface GereRepository extends JpaRepository<Gere, Long> {

	@Query("from Gere g WHERE g.macroRegiao=:macro ORDER BY g.nome")
	List<Gere> listarPorMacro(@Param("macro") MacroRegiao macro);

	@Query("from Gere g ORDER BY g.nome")
	List<Gere> listarTodos();

}
