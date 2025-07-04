package br.gov.pe.ses.starter.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.pe.ses.starter.entidades.publico.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    List<Unidade> findAll(Specification<Unidade> spec);

    Page<Unidade> findAll(Specification<Unidade> spec, Pageable page);

    @Query("from Unidade h where h.ativo is true order by h.nome")
    List<Unidade> findAllAtivos();

    boolean existsUnidadeByCnpj(String cnpj);
    boolean existsUnidadeByCnes(String cnes);

    @Query("SELECT COUNT(u) > 0 FROM Unidade u WHERE u.cnpj = :#{#unidade.cnpj} AND (:#{#unidade.id} IS NULL OR u.id <> :#{#unidade.id})")
    boolean existsByCnpjAndNotSameId(Unidade unidade);

    @Query("SELECT COUNT(u) > 0 FROM Unidade u WHERE u.cnes = :#{#unidade.cnes} AND (:#{#unidade.id} IS NULL OR u.id <> :#{#unidade.id})")
    boolean existsByCnesAndNotSameId(Unidade unidade);

}
