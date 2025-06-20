package br.gov.pe.ses.starter.data.specifications;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.gov.pe.ses.starter.dto.UsuarioFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.util.jsf.FacesUtil;
import jakarta.persistence.criteria.Predicate;

public class UsuarioEspecification {

	public static Specification<Usuario> build(UsuarioFiltroDTO filtro) {

		return (root, query, criteriaBuilder) -> {

			List<Predicate> criterios = new ArrayList<>();

			if (ObjectUtils.allNotNull(filtro)) {

				if (StringUtils.isNotEmpty((String) filtro.getNome())) {
					String parametro = "%" + ((String) filtro.getNome()).toLowerCase() + "%";

					Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("pessoa").get("nome")),
							parametro);

					criterios.add(likeLower);
				}

				if (StringUtils.isNotEmpty((String) filtro.getLogin())) {
					Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("login")),
							"%" + ((String) filtro.getLogin()).toLowerCase() + "%");

					criterios.add(likeLower);
				}

				if (StringUtils.isNotEmpty((String) filtro.getEmail())) {
					Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("pessoa").get("email")),
							"%" + ((String) filtro.getEmail()).toLowerCase() + "%");

					criterios.add(likeLower);
				}

				criterios.add(criteriaBuilder.equal(root.get("unidade"), FacesUtil.getUnidadeSelecionado()));

			}

			return criteriaBuilder.and(criterios.toArray(new Predicate[0]));
		};
	}

}
