package br.gov.pe.ses.starter.data.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.gov.pe.ses.starter.dto.TipoUnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.TipoUnidade;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;

public class TipoUnidadeEspecification {

	public static Specification<TipoUnidade> build(TipoUnidadeFiltroDTO filtro) {

		return (root, query, criteriaBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.isNotEmpty(filtro.getDescricao())) {
				Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")),
						"%" + filtro.getDescricao().toLowerCase() + "%");
				predicates.add(likeLower);

			}

			if (filtro.getStatus() != null) {
				Predicate statusPredicate = criteriaBuilder.equal(root.get("ativo"), filtro.getStatus());
				predicates.add(statusPredicate);
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
