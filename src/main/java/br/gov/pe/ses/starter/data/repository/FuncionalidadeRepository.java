package br.gov.pe.ses.starter.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Funcionalidade;

@Repository
public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {

	@Query("SELECT f FROM Funcionalidade f WHERE f.id = f.funcionalidadePai.id ORDER BY f.nome")
	List<Funcionalidade> buscarPrincipais();

	@Query("SELECT f FROM Funcionalidade f WHERE f.funcionalidadePai =:pai AND f.id != f.funcionalidadePai.id ORDER BY f.nome")
	List<Funcionalidade> buscarFilhas(@Param("pai") Funcionalidade pai);
	
	@Query("SELECT f FROM Funcionalidade f")
	List<Funcionalidade> buscarTodas();

}
