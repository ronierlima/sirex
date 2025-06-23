package br.gov.pe.ses.starter.data.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;

public class HospitalEspecification {

	public static Specification<Unidade> build(UnidadeFiltroDTO filtro) {

		return (root, query, criteriaBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.isNotEmpty(filtro.getNome())) {
				Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")),
						"%" + filtro.getNome().toLowerCase() + "%");
				predicates.add(likeLower);

			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
