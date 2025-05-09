package br.gov.pe.ses.starter.data.specifications;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

import br.gov.pe.ses.starter.dto.PerfilFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Hospital;
import br.gov.pe.ses.starter.entidades.publico.Perfil;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;

public class PerfilEspecification {

	public static Specification<Perfil> build(PerfilFiltroDTO filtro) {

		return (root, query, criteriaBuilder) -> {
			
			Hospital hospitalSelecionado = FacesUtil.getHospitalSelecionado();

			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.isNotEmpty(filtro.getNome())) {
				Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")),
						"%" + filtro.getNome().toLowerCase() + "%");
				predicates.add(likeLower);

			}
			
			predicates.add(criteriaBuilder.equal(root.get("hospital").get("id"), hospitalSelecionado.getId()));

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			
		};
	}

}
