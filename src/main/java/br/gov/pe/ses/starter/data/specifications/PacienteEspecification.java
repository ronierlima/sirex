package br.gov.pe.ses.starter.data.specifications;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.gov.pe.ses.starter.dto.PacienteFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Paciente;
import jakarta.persistence.criteria.Predicate;

public class PacienteEspecification {

	public static Specification<Paciente> build(PacienteFiltroDTO filtro) {

		return (root, query, criteriaBuilder) -> {

			List<Predicate> criterios = new ArrayList<>();

			if (ObjectUtils.allNotNull(filtro)) {

				if (StringUtils.isNotEmpty(filtro.getNome())) {
					String parametro = "%" + (filtro.getNome()).toLowerCase() + "%";

					Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("pessoa").get("nome")),
							parametro);

					criterios.add(likeLower);
				}

				if (StringUtils.isNotEmpty(filtro.getCartaoSus())) {
					String parametro = "%" + filtro.getCartaoSus().toLowerCase() + "%";

					Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("cartaoSus")), parametro);

					criterios.add(likeLower);
				}

				if (StringUtils.isNotEmpty(filtro.getProntuario())) {
					String parametro = "%" + filtro.getProntuario().toLowerCase() + "%";

					Predicate likeLower = criteriaBuilder.like(criteriaBuilder.lower(root.get("prontuario")),
							parametro);

					criterios.add(likeLower);
				}

				if (filtro.getStatus() != null) {
					criterios.add(criteriaBuilder.equal(root.get("ativo"), filtro.getStatus()));
				}

				if (filtro.getUnidade() != null) {
					criterios.add(criteriaBuilder.equal(root.get("unidadeCadastro"), filtro.getUnidade()));
				}

			}

			return criteriaBuilder.and(criterios.toArray(new Predicate[0]));
		};
	}

}
