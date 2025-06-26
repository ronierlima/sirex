package br.gov.pe.ses.starter.data.specifications;

import br.gov.pe.ses.starter.builders.SpecificationBuilder;
import br.gov.pe.ses.starter.dto.UnidadeFiltroDTO;
import br.gov.pe.ses.starter.entidades.publico.Unidade;
import org.springframework.data.jpa.domain.Specification;

public class UnidadeEspecification {

    public static Specification<Unidade> build(UnidadeFiltroDTO filtro) {

        return (root, query, criteriaBuilder) -> {


            SpecificationBuilder<Unidade> builder = SpecificationBuilder.create(root, criteriaBuilder);

            builder.whereIlike("nome", filtro.getNome(), SpecificationBuilder.MatchMode.ANYWHERE);
            builder.whereEqual("ativo", filtro.getStatus());
            builder.whereIn("tipo.id", filtro.getTipos());
            builder.whereIn("municipio.gere.id", filtro.getGeres());
            builder.whereIn("municipio.id", filtro.getMunicipios());

            return builder.build();
        };
    }

}
